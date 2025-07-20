package com.talendradar.tests.api.auth;

import com.talendradar.data.pojo.api.login.ApiLoginExpectedPojo;
import com.talendradar.data.providers.api.LoginDataProvider;
import com.talentradar.api.AuthApi;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class EmailPasswordLoginStrategyTests extends AuthBaseTest {

  @Test(dataProvider = "success", dataProviderClass = LoginDataProvider.class)
  @Severity(SeverityLevel.BLOCKER)
  @Description("Verify {desc} can log in successfully with valid credentials")
  public void verifyUserCanLoginSuccessfully(String desc,
                                             Map<String, Object> credentials,
                                             ApiLoginExpectedPojo expected) {
    AuthApi.authenticateUser(credentials, expected.status())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.schema()))
      .body("data.user.role", equalTo(expected.role()));
  }

  @Test(dataProvider = "unauthorized", dataProviderClass = LoginDataProvider.class)
  @Severity(SeverityLevel.BLOCKER)
  @Description("Verify login fails with {desc}")
  public void verifyLoginFailsWithInvalidCredentials(String desc,
                                                     Map<String, Object> credentials,
                                                     ApiLoginExpectedPojo expected) {
    AuthApi.authenticateUser(credentials, expected.status())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.schema()));
  }
}
