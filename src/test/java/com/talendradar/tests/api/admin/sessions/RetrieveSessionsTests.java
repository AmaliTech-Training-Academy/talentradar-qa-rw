package com.talendradar.tests.api.admin.sessions;

import com.talendradar.data.pojo.api.sessions.ApiSessionExpectedPojo;
import com.talendradar.data.providers.api.SessionsDataProvider;
import com.talentradar.api.SessionApi;
import com.talentradar.utils.JwtUtil;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class RetrieveSessionsTests extends SessionBaseTest {

  @Test(dataProvider = "unauthorized", dataProviderClass = SessionsDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify only admin can view user sessions")
  public void verifyOnlyAdminCanViewSessions(String role, ApiSessionExpectedPojo expected) {
    SessionApi.getSessions(JwtUtil.generateValidToken(role), expected.status())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.schema()));
  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify admin can view user sessions")
  public void verifyAdminCanViewSessions() {
    SessionApi.getSessions(JwtUtil.generateValidToken("admin"), 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/admin/sessions/sessionList.json"));
  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify sessions filtering")
  public void verifyUserSessionsFiltering() {
    SessionApi.filterSessions(JwtUtil.generateValidToken("admin"), Map.of(), 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/admin/sessions/sessionList.json"));
  }
}
