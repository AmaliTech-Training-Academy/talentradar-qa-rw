package com.talendradar.tests.api.users;

import com.talendradar.data.pojo.api.users.ApiUsersExpectedPojo;
import com.talendradar.data.providers.api.UsersDataProvider;
import com.talendradar.tests.api.APIBaseTest;
import com.talentradar.api.UserApi;
import com.talentradar.utils.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetUsersTests extends APIBaseTest {

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify the route is protected")
  public void verifyGetUsersFailsWithoutAuthentication() {
    UserApi.getUsers("", 401)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/common/accessDenied.json"));
  }

  @Test(dataProvider = "allOnlyAdmin", dataProviderClass = UsersDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify only admin can access GET:/users")
  public void verifyGetUsersIsOnlyAccessibleByAdmin(String role, ApiUsersExpectedPojo expected) {
    UserApi.getUsers(JwtUtil.generateValidToken(role), expected.status())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.schema()));
  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify admin can fetch all users")
  public void verifyAdminCanFetchAllUsers() {
    UserApi.getUsers(JwtUtil.generateValidToken("admin"), 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/users/userList.json"));
  }
}
