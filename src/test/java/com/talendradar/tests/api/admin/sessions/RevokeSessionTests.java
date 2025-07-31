package com.talendradar.tests.api.admin.sessions;

import com.talentradar.api.SessionApi;
import com.talentradar.util.ApiUtil;
import com.talentradar.util.Envs;
import com.talentradar.util.JwtUtil;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class RevokeSessionTests extends SessionBaseTest {

//  @Test(dataProvider = "unauthorized", dataProviderClass = SessionsDataProvider.class)
//  @Severity(SeverityLevel.CRITICAL)
//  @Description("Verify only admin can revoke session")
//  public void verifyOnlyAdminCanRevokeSession(String role, ApiSessionExpectedPojo expected) {
//    SessionApi.revokeSession("some-session-uuid", JwtUtil.generateValidToken(role), expected.status())
//      .then()
//      .body(matchesJsonSchemaInClasspath(expected.schema()));
//  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify admin can revoke session")
  void verifyAdminCanRevokeSession() {
    // Login as a developer
    ApiUtil.login(Map.of("email", Envs.DEVELOPER_EMAIL, "password", Envs.ANY_USER_PASS));

    // Generate admin auth token
    String token = JwtUtil.generateValidToken("admin");

    // Get session id
    String sessionId = (String) ApiUtil.getSessionId(token, Envs.DEVELOPER_ID);

    // Revoke session
    SessionApi.revokeSession(sessionId, token, 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/admin/sessions/revoked.json"));
  }
}
