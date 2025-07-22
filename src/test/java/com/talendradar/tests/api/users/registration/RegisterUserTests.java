package com.talendradar.tests.api.users.registration;

import com.talendradar.data.pojo.api.registration.ApiRegExpectedPojo;
import com.talendradar.data.providers.api.RegistrationDataProvider;
import com.talentradar.api.UserApi;
import com.talentradar.utils.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class RegisterUserTests extends UserRegistrationBaseTest {

  @Test(dataProvider = "onlyAdminCanInviteUser", dataProviderClass = RegistrationDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify only admin can create registration invite")
  public void verifyOnlyAdminCanRegisterUser(String role, Map<String, Object> newUser, ApiRegExpectedPojo expected) {
    UserApi.registerUser(JwtUtil.generateValidToken(role), newUser, expected.status())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.schema()));
  }

  @Test(dataProvider = "invalidFields", dataProviderClass = RegistrationDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify registration fails given {desc}")
  public void verifyRegistrationFailsGivenInvalidUserInfo(String desc,
                                                          Map<String, Object> newUser,
                                                          ApiRegExpectedPojo expected) {
    UserApi.registerUser(JwtUtil.generateValidToken("admin"), newUser, expected.status())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.schema()));
  }

  @Test(dataProvider = "invite", dataProviderClass = RegistrationDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify admin can create and send registration invites")
  public void verifyAdminCanRegisterUser(Map<String, Object> newUser, ApiRegExpectedPojo expected) {
    UserApi.registerUser(JwtUtil.generateValidToken("admin"), newUser, expected.status())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.schema()));
  }
}
