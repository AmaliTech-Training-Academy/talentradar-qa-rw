package com.talendradar.tests.api.roles;

import com.talendradar.data.pojo.api.roles.ApiRolesExpectedPojo;
import com.talendradar.data.providers.api.RolesDataProvider;
import com.talendradar.tests.api.APIBaseTest;
import com.talentradar.api.RolesApi;
import com.talentradar.utils.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetRolesTests extends APIBaseTest {

  @Test(dataProvider = "onlyAdminCanQueryAllRoles", dataProviderClass = RolesDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify only admin can access GET:/roles")
  public void verifyGetRolesIsOnlyAccessibleByAdmin(String role, ApiRolesExpectedPojo expected) {
    RolesApi.getRoles(JwtUtil.generateValidToken(role), expected.status())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.schema()));
  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify admin can fetch all roles")
  public void verifyAdminCanFetchAllRoles() {
    RolesApi.getRoles(JwtUtil.generateValidToken("admin"), 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/roles/roleList.json"));
  }
}
