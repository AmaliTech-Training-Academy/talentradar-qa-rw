package com.talendradar.tests.api.users;

import com.talendradar.providers.api.UserDataProvider;
import com.talendradar.tests.api.APIBaseTest;
import com.talentradar.api.UserApi;
import com.talentradar.dto.ApiExpectedResponseDto;
import com.talentradar.dto.ApiRequestDto;
import com.talentradar.util.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class GetCurrentUserTests extends APIBaseTest {

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify access to GET:/users/me route requires authentication")
  void verifyGetCurrentUserDetailsFailsWithoutAuthentication() {
    UserApi.getCurrentUserDetails("", 401)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/users/unauthorizedGetMe.json"));
  }

//  @Test(dataProvider = "inactive-or-deleted", dataProviderClass = AuthDataProvider.class)
//  @Tag("Security")
//  @Severity(SeverityLevel.CRITICAL)
//  @Description("Verify access to GET:/users/me is denied given auth-token has claims of inactive/deleted user")
//  public void verifyTokenFailsForInactiveOrDeletedUserClaims(AuthClaimsDto claims, ApiExpectedResponseDto expected) {
//    UserApi.getCurrentUserDetails(JwtUtil
//        .generateToken(claims, JWT_SECRET, new Date(),
//          new Date(System.currentTimeMillis() + 15 * 60 * 1000)), expected.status())
//      .then()
//      .body(matchesJsonSchemaInClasspath(expected.schema()));
//  }

  @Test(dataProvider = "current-user", dataProviderClass = UserDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify GET:/users/me route returns info pertained to the authenticated user")
  void verifyGetCurrentUserReturnsAuthenticatedUserInfo(ApiRequestDto request, ApiExpectedResponseDto expected) {
    UserApi.getCurrentUserDetails(JwtUtil.generateValidToken(request.getRole()), expected.getStatus())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.getSchema()))
      .body("data.user.role", equalTo(expected.getRole()))
      .body("data.user.id", equalTo(getDefaultCurrentUserId(request.getRole())));
  }
}
