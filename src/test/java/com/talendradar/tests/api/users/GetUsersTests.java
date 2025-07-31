package com.talendradar.tests.api.users;

import com.talendradar.providers.api.AuthDataProvider;
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

public class GetUsersTests extends APIBaseTest {

  @Test(dataProvider = "admin-role-based-protected", dataProviderClass = AuthDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify GET:/users route is only admin accessible")
  void verifyGetUsersIsOnlyAdminAccessible(ApiRequestDto request, ApiExpectedResponseDto expected) {
    UserApi.getUsers(JwtUtil.generateValidToken(request.getRole()), expected.getStatus())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.getSchema()));
  }

//  @Test(dataProvider = "inactive-or-deleted", dataProviderClass = AuthDataProvider.class)
//  @Tag("Security")
//  @Severity(SeverityLevel.CRITICAL)
//  @Description("Verify access to GET:/users is denied given auth-token has claims of inactive/deleted user")
//  public void verifyTokenFailsForInactiveOrDeletedUserClaims(AuthClaimsDto claims, ApiExpectedResponseDto expected) {
//    UserApi.getUsers(JwtUtil
//        .generateToken(claims, JWT_SECRET, new Date(),
//          new Date(System.currentTimeMillis() + 15 * 60 * 1000)), expected.status())
//      .then()
//      .body(matchesJsonSchemaInClasspath(expected.schema()));
//  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify admin can view all users")
  void verifyAdminCanViewUsers() {
    UserApi.getUsers(JwtUtil.generateValidToken("admin"), 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/users/userList.json"));
  }
}
