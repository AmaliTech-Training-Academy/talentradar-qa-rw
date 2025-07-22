package com.talendradar.tests.api;

import com.talendradar.data.providers.api.RolesDataProvider;
import com.talentradar.api.AssessmentApi;
import com.talentradar.utils.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

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

  @Test(dataProvider = "role-labels", dataProviderClass = RolesDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify any authenticated users can fetch dimensions")
  public void verifyAnyAuthenticatedUserCanFetchDimensions(String role) {;
    AssessmentApi.getDimensions(JwtUtil.generateValidToken(role), 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/common/dimensionList.json"));
  }
}
