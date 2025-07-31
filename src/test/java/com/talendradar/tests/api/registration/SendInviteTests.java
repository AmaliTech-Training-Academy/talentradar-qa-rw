package com.talendradar.tests.api.registration;

import com.talendradar.providers.api.RegistrationDataProvider;
import com.talentradar.api.UserApi;
import com.talentradar.dto.ApiExpectedResponseDto;
import com.talentradar.dto.ApiRequestDto;
import com.talentradar.dto.RegisteredUserResponseDto;
import com.talentradar.dto.UserResponseDto;
import com.talentradar.util.ApiUtil;
import com.talentradar.util.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SendInviteTests extends UserRegistrationBaseTest {

  @Test(dataProvider = "only-admin-is-allowed-to-send-invite", dataProviderClass = RegistrationDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify only admin can create registration invite")
  void verifyOnlyAdminCanRegisterUser(ApiRequestDto request, ApiExpectedResponseDto expected) {
    compareUserListToEnsureNoUserWasRegistered(() -> UserApi
      .registerUser(JwtUtil.generateValidToken(request.getRole()), request.asSendInvite(), expected.getStatus())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.getSchema()))
      .extract()
      .response()
    );
  }

  @Test(dataProvider = "send-invite-validation", dataProviderClass = RegistrationDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify user registration fails given {desc}")
  void verifyRegistrationIsValidated(String desc, ApiRequestDto req, ApiExpectedResponseDto expected) {
    compareUserListToEnsureNoUserWasRegistered(() -> UserApi
      .registerUser(JwtUtil.generateValidToken("admin"), req.asSendInvite(), expected.getStatus())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.getSchema()))
      .extract()
      .response()
    );
  }

  @Test(dataProvider = "send-invite", dataProviderClass = RegistrationDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify admin can create and send registration invites")
  void verifyAdminCanRegisterUser(ApiRequestDto request, ApiExpectedResponseDto expected) {
    // Fetch default user list
    List<UserResponseDto> originalList = ApiUtil.getAllUsers();

    // Register new user
    RegisteredUserResponseDto regUser = UserApi.registerUser(JwtUtil
        .generateValidToken("admin"), request.asSendInvite(), expected.getStatus())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.getSchema()))
      .body("roleName", equalTo(expected.getRole()))
      .extract()
      .as(RegisteredUserResponseDto.class);

    // Fetch user list after registration
    List<UserResponseDto> currentList = ApiUtil.getAllUsers();

    // After registration, the original user list should be different from the current one
    assertThat(originalList, is(not(equalTo(currentList))));

    // Filter out the registered user
    Optional<UserResponseDto> registeredUser = currentList.stream()
      .filter(user -> regUser.id().equals(user.id()))
      .findFirst();

    // registeredUser should not be null
    Assert.assertTrue(registeredUser.isPresent());

    // Remove the registered user from the current user list and recompare the lists
    currentList.remove(registeredUser.get());
    assertThat(originalList, is(equalTo(currentList)));

    // Finally, assert registered info against response and provided details
    assertThat(request.getEmail(), equalTo(regUser.email()));
    assertThat(request.getEmail(), equalTo(registeredUser.get().email()));
    assertThat(expected.getRole(), equalTo(registeredUser.get().role()));
  }
}
