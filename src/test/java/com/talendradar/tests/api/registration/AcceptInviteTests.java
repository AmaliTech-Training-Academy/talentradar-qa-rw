package com.talendradar.tests.api.registration;

import com.talendradar.providers.api.RegistrationDataProvider;
import com.talentradar.api.UserApi;
import com.talentradar.dto.*;
import com.talentradar.util.ApiUtil;
import com.talentradar.util.Envs;
import com.talentradar.util.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AcceptInviteTests extends UserRegistrationBaseTest {

  @Test(dataProvider = "already-changed-user", dataProviderClass = RegistrationDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify registration invite fails when signed user is {desc}")
  void verifyInviteWhoseSignedUserIsAlreadyConfirmedOrDeleted(String desc,
                                                              NewUserInviteClaimsDto claims,
                                                              ApiExpectedResponseDto expected) {
    // Generate registration invite
    String invite = JwtUtil.generateValidRegInvite(claims);

    // Now, attempt to complete registration
    compareUserListToEnsureNoUserWasRegistered(() -> UserApi
      .completeRegistration(invite, RegistrationDataProvider.acceptInviteAnonymousRequest(), expected.getStatus())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.getSchema()))
      .extract()
      .response()
    );
  }

  @Test(dataProvider = "accept-invite-validation", dataProviderClass = RegistrationDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify completing registration fails given {desc}")
  void verifyAcceptInviteFlowIsValidated(String desc, ApiSceneDto inviteScene,
                                         ApiRequestDto request, ApiExpectedResponseDto expected) {
    // Generate registration invite for the registered user
    String invite = ApiUtil.registerAndGetInvite(inviteScene);

    // Now, attempt to complete registration
    compareUserListToEnsureNoUserWasRegistered(() -> UserApi
      .completeRegistration(invite, request.asAcceptInvite(), expected.getStatus())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.getSchema()))
      .extract()
      .response()
    );
  }

  @Test(dataProvider = "expired-invite", dataProviderClass = RegistrationDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify expired registration invite is validated and rejected")
  void verifyAcceptInviteFlowFailsGivenExpiredInvite(ApiSceneDto inviteScene,
                                                     ApiRequestDto request, ApiExpectedResponseDto expected) {
    // Register user and get user ID
    String id = ApiUtil.register(JwtUtil.generateValidToken("admin"),
                                 inviteScene.getRequest(), inviteScene.getExpected());

    // Now, let's generate an expired registration invite
    String invite = JwtUtil.generateRegistrationInvite(inviteScene.getRequest().asNewUserInviteClaims(id),
                                                       Envs.JWT_SECRET,
                                                       new Date(System.currentTimeMillis() - 200L),
                                                       new Date(System.currentTimeMillis() - 100L));

    // Now, attempt to complete registration
    compareUserListToEnsureNoUserWasRegistered(() -> UserApi
      .completeRegistration(invite, request.asAcceptInvite(), expected.getStatus())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.getSchema()))
      .extract()
      .response()
    );
  }

  @Test(dataProvider = "accept-invite", dataProviderClass = RegistrationDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify user can complete registration given valid invite token")
  void verifyUserCanAcceptInvite(ApiSceneDto inviteScene, ApiRequestDto request, ApiExpectedResponseDto expected) {
    // Generate registration invite for the registered user
    String invite = ApiUtil.registerAndGetInvite(inviteScene);

    // Fetch default user list
    List<UserResponseDto> originalList = ApiUtil.getAllUsers();

    // Now, complete registration
    RegisteredUserResponseDto regUser = UserApi
      .completeRegistration(invite, request.asAcceptInvite(), expected.getStatus())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.getSchema()))
      .body("status", equalTo("ACTIVE"))
      .extract()
      .as(RegisteredUserResponseDto.class);

    // Fetch user list after registration
    List<UserResponseDto> currentList = ApiUtil.getAllUsers();

    // After registration, the original user list should be different from the current one
    // But size should be the same
    assertThat(originalList, is(not(equalTo(currentList))));
    assertThat(originalList.size(), equalTo(currentList.size()));

    // Filter out the registered user
    Optional<UserResponseDto> registeredUser = currentList.stream()
      .filter(user -> regUser.id().equals(user.id()))
      .findFirst();

    // registeredUser should not be null
    Assert.assertTrue(registeredUser.isPresent());

    // Remove registered user and recompare the lists
    originalList.remove(registeredUser.get());
    currentList.remove(registeredUser.get());
    assertThat(originalList, is(equalTo(currentList)));

    // Finally, assert registered info against response and provided details
    assertThat(inviteScene.getRequest().getEmail(), equalTo(regUser.email()));
    assertThat(inviteScene.getRequest().getEmail(), equalTo(registeredUser.get().email()));
    assertThat(inviteScene.getExpected().getRole(), equalTo(registeredUser.get().role()));
  }
}
