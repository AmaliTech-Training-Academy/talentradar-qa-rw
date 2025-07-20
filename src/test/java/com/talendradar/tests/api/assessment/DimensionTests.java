package com.talendradar.tests.api.assessment;

import com.talendradar.tests.api.APIBaseTest;
import com.talentradar.api.AssessmentApi;
import com.talentradar.utils.EnvUtil;
import com.talentradar.utils.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DimensionTests extends APIBaseTest {

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify fetching dimensions fails without authentication")
  public void verifyGetDimensionsFailsWithoutAuthentication() {
    AssessmentApi.getDimensions("", 401)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/assessment/authenticationRequired.json"));
  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify fetching dimensions when authenticated")
  public void verifyGetDimensionsWhenAuthenticated() {
    String token = JwtUtil.generateToken(Map.of("email", "admin@talentradar.ai"),
                                         EnvUtil.getEnv("JWT_SECRET"),
                                         new Date(),
                                         new Date(System.currentTimeMillis() + 15 * 60 * 1000));
    AssessmentApi.getDimensions(token, 401)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/assessment/authenticationRequired.json"));
  }
}
