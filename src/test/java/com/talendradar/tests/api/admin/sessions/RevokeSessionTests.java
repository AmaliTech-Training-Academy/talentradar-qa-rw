package com.talendradar.tests.api.admin.sessions;

import com.talendradar.data.pojo.api.sessions.ApiSessionExpectedPojo;
import com.talendradar.data.providers.api.SessionsDataProvider;
import com.talentradar.api.SessionApi;
import com.talentradar.utils.APIUtil;
import com.talentradar.utils.JwtUtil;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class RevokeSessionTests extends SessionBaseTest {

  @Test(dataProvider = "unauthorized", dataProviderClass = SessionsDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify only admin can revoke session")
  public void verifyOnlyAdminCanRevokeSession(String role, ApiSessionExpectedPojo expected) {
    SessionApi.revokeSession("some-session-uuid", JwtUtil.generateValidToken(role), expected.status())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.schema()));
  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify admin can revoke session")
  public void verifyAdminCanRevokeSession() {
    // Login as a developer
    APIUtil.login(Map.of("email", DEVELOPER_EMAIL, "password", DEVELOPER_PASS));

    // Generate admin auth token
    String token = JwtUtil.generateValidToken("admin");

    // Get session id
    String sessionId = (String) APIUtil.getSessionId(token, DEVELOPER_ID);

    // Revoke session
    SessionApi.revokeSession(sessionId, token, 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/admin/sessions/revoked.json"));
  }
}
