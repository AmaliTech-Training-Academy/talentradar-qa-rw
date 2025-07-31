package com.talendradar.tests.api.auth;

import com.talendradar.providers.api.AuthDataProvider;
import com.talentradar.api.AuthApi;
import com.talentradar.dto.ApiExpectedResponseDto;
import com.talentradar.dto.ApiRequestDto;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class EmailPasswordLoginStrategyTests extends AuthBaseTest {

  @Test(dataProvider = "login-success", dataProviderClass = AuthDataProvider.class)
  @Severity(SeverityLevel.BLOCKER)
  @Description("Verify {desc} can log in successfully with valid credentials")
  void verifySuccessfulLogin(String desc, ApiRequestDto request, ApiExpectedResponseDto expected) {
    AuthApi.authenticateUser(request.asLogin(), expected.getStatus())
      .then()
      .cookie("token", not(nullValue()))
      .header("Authorization", nullValue())
      .body(matchesJsonSchemaInClasspath(expected.getSchema()))
      .body("data.user.role", equalTo(expected.getRole()));
  }

  @Test(dataProvider = "login-failure", dataProviderClass = AuthDataProvider.class)
  @Severity(SeverityLevel.BLOCKER)
  @Description("Verify login fails with {desc}")
  void verifyLoginFailures(String desc,  ApiRequestDto request, ApiExpectedResponseDto expected) {
    AuthApi.authenticateUser(request.asLogin(), expected.getStatus())
      .then()
      .cookie("token", nullValue())
      .header("Authorization", nullValue())
      .body(matchesJsonSchemaInClasspath(expected.getSchema()));
  }
}
