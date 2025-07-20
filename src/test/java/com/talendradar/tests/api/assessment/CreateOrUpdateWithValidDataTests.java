package com.talendradar.tests.api.assessment;

import com.talendradar.data.pojo.api.assessment.ApiAssessExpectedPojo;
import com.talendradar.data.pojo.api.assessment.ApiAssessCreatePojo;
import com.talendradar.data.providers.api.SelfAssessmentDataProvider;
import com.talentradar.api.AssessmentApi;
import com.talentradar.utils.APIUtil;
import com.talentradar.utils.JwtUtil;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrUpdateWithValidDataTests extends AssessmentBaseTest {

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify developer can {desc}")
  public void verifyDeveloperCanCompleteSelfAssessment() {
    ApiAssessCreatePojo source = SelfAssessmentDataProvider.load().selfAssessment().create();
    assertSelfAssessing(DEVELOPER_ID, source.payload(), source.expected());
  }

  @Test(dataProvider = "update", dataProviderClass = SelfAssessmentDataProvider.class)
  @Severity(SeverityLevel.NORMAL)
  @Description("Verify developer can {desc}")
  public void verifyDeveloperCanUpdateSelfAssessment(ApiAssessCreatePojo create,
                                                     Map<String, Object> payload,
                                                     ApiAssessExpectedPojo expected) {
    // Submit self-assessment first and extract its id
    String assessmentId = assertSelfAssessing(DEVELOPER_ID, create.payload(), create.expected())
      .path("data.assessment.id");

    // Generate another auth token but same user
    String token = JwtUtil.generateValidToken("developer");

    // Aggregate payload
    APIUtil.refineSelfAssessmentPayload(token, payload);

    // Now, update self-assessment
    AssessmentApi.update(assessmentId, payload, token, expected.status())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.schema()))
      .body("data.assessment.status", equalTo(payload.get("status")));
  }
}
