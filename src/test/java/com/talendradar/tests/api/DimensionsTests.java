package com.talendradar.tests.api;

import com.talendradar.providers.api.AuthDataProvider;
import com.talentradar.api.AssessmentApi;
import com.talentradar.util.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DimensionsTests extends APIBaseTest {

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify GET:/dimensions requires authentication")
  void verifyGetDimensionsFailsWhenRequestIsNotAuthenticated() {
    AssessmentApi.getDimensions("", 401)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/auth/unauthorized.json"));
  }

//  @Test(dataProvider = "inactive-or-deleted", dataProviderClass = AuthDataProvider.class)
//  @Tag("Security")
//  @Severity(SeverityLevel.CRITICAL)
//  @Description("Verify access to GET:/dimensions is denied given auth-token has claims of inactive/deleted user")
//  public void verifyTokenFailsForInactiveOrDeletedUserClaims(AuthClaimsDto claims, ApiExpectedResponseDto expected) {
//    AssessmentApi.getDimensions(JwtUtil
//        .generateToken(claims, JWT_SECRET, new Date(),
//                       new Date(System.currentTimeMillis() + 15 * 60 * 1000)), expected.status())
//      .then()
//      .body(matchesJsonSchemaInClasspath(expected.schema()));
//  }

  @Test(dataProvider = "any-role-accessible", dataProviderClass = AuthDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify GET:/dimensions route is accessible to any authenticated personnel")
  void verifyGetDimensionsIsAccessibleToAuthenticatedUser(String role) {;
    AssessmentApi.getDimensions(JwtUtil.generateValidToken(role), 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/common/dimensionList.json"));
  }
}
