package com.talendradar.tests.api.admin.sessions;

import com.talentradar.api.SessionApi;
import com.talentradar.util.JwtUtil;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class RetrieveSessionsTests extends SessionBaseTest {

//  @Test(dataProvider = "unauthorized", dataProviderClass = SessionsDataProvider.class)
//  @Severity(SeverityLevel.CRITICAL)
//  @Description("Verify only admin can view user sessions")
//  void verifyOnlyAdminCanViewSessions(String role, ApiSessionExpectedPojo expected) {
//    SessionApi.getSessions(JwtUtil.generateValidToken(role), expected.status())
//      .then()
//      .body(matchesJsonSchemaInClasspath(expected.schema()));
//  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify admin can view user sessions")
  void verifyAdminCanViewSessions() {
    SessionApi.getSessions(JwtUtil.generateValidToken("admin"), 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/admin/sessions/sessionList.json"));
  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify sessions filtering")
  void verifyUserSessionsFiltering() {
    SessionApi.filterSessions(JwtUtil.generateValidToken("admin"), Map.of(), 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/admin/sessions/sessionList.json"));
  }
}
