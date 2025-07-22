package com.talendradar.tests.api.users.registration;

import com.talendradar.data.pojo.api.registration.ApiRegExpectedPojo;
import com.talendradar.data.pojo.api.registration.ApiRegInviteNewUserPojo;
import com.talendradar.data.providers.api.RegistrationDataProvider;
import com.talentradar.api.UserApi;
import com.talentradar.pojo.NewUserDetails;
import com.talentradar.utils.APIUtil;
import com.talentradar.utils.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CompleteRegistrationTests extends UserRegistrationBaseTest {

  @Test(dataProvider = "completeRegistration", dataProviderClass = RegistrationDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify user registration full flow")
  public void verifyUserRegistrationJourney(ApiRegInviteNewUserPojo invite, Map<String, Object> payload, ApiRegExpectedPojo expected) {
    String id = APIUtil.register(JwtUtil.generateValidToken("admin"),
                                 invite.newUser(), invite.expected().status(), invite.expected().schema());

    String invitation = JwtUtil.generateRegistrationInviteToken(
      new NewUserDetails(id, (String) invite.newUser().get("email"), (String) invite.newUser().get("roleId")),
      JWT_SECRET,
      new Date(),
      new Date(System.currentTimeMillis() + 15 * 60 * 1000));

    UserApi.completeRegistration(invitation, payload, expected.status())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.schema()));
  }
}
