package com.talendradar.tests.api.users;

import com.talendradar.data.pojo.api.users.ApiMeExpectedPojo;
import com.talendradar.data.providers.api.UsersDataProvider;
import com.talendradar.tests.api.APIBaseTest;
import com.talentradar.api.UserApi;
import com.talentradar.utils.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class GetCurrentUserTests extends APIBaseTest {

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify the GET:/users/me route is protected")
  public void verifyGetCurrentUserDetailsFailsWithoutAuthentication() {
    UserApi.getCurrentUserDetails("", 401)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/users/unauthorizedGetMe.json"));
  }

  @Test(dataProvider = "currentUser", dataProviderClass = UsersDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify any authenticated user can access the route")
  public void verifyGetCurrentUserSucceedsWhenAuthenticated(String role, ApiMeExpectedPojo expected) {
    UserApi.getCurrentUserDetails(JwtUtil.generateValidToken(role), expected.status())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.schema()))
      .body("data.user.role", equalTo(expected.role()))
      .body("data.user.id", equalTo(getDefaultCurrentUserId(role)));
  }
}
