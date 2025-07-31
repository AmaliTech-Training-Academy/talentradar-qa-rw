package com.talendradar.tests.api;

import com.talendradar.providers.api.AuthDataProvider;
import com.talentradar.api.RolesApi;
import com.talentradar.dto.ApiExpectedResponseDto;
import com.talentradar.dto.ApiRequestDto;
import com.talentradar.util.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class RolesTests extends APIBaseTest {

  @Test(dataProvider = "admin-role-based-protected", dataProviderClass = AuthDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify only admin can access GET:/roles")
  void verifyGetRolesIsOnlyAccessibleByAdmin(ApiRequestDto request, ApiExpectedResponseDto expected) {
    RolesApi.getRoles(JwtUtil.generateValidToken(request.getRole()), expected.getStatus())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.getSchema()));
  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify admin can access roles")
  void verifyAdminCanGetRoles() {
    RolesApi.getRoles(JwtUtil.generateValidToken("admin"), 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/roles/roleList.json"));
  }
}
