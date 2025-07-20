package com.talendradar.tests.api.assessment;

import com.talendradar.data.pojo.api.assessment.ApiAssessCreatePojo;
import com.talendradar.data.pojo.api.assessment.ApiAssessExpectedPojo;
import com.talendradar.data.pojo.api.assessment.ApiAssessUpdateScenarioPojo;
import com.talendradar.data.providers.api.APIBaseDataProvider;
import com.talendradar.data.providers.api.SelfAssessmentDataProvider;
import com.talentradar.api.AssessmentApi;
import com.talentradar.utils.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateOrUpdateWithInvalidDataTests extends AssessmentBaseTest {

  @Test(dataProvider = "onlyDeveloper", dataProviderClass = SelfAssessmentDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify only developer can create self-assessment")
  public void verifyOnlyDeveloperCreateAssessment(String role, int expectedStatus) {
    AssessmentApi.create(Map.of(), JwtUtil.generateValidToken(role), expectedStatus);
  }

  @Test(dataProvider = "onlyDeveloper", dataProviderClass = SelfAssessmentDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify only developer can update self-assessment")
  public void verifyOnlyDeveloperCanUpdateAssessment(String role, int expectedStatus) {
    AssessmentApi.update("some-uuid", Map.of(), JwtUtil.generateValidToken(role), expectedStatus);
  }

  @Test
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify once a self-assessment is complete, it can never be modified again.")
  public void verifyCompleteAssessmentCannotBeUpdated() {
    ApiAssessUpdateScenarioPojo src = APIBaseDataProvider.load().selfAssessment().alreadyComplete();
    // Submit self-assessment first and extract its id
    String assessmentId = assertSelfAssessing(DEVELOPER_ID,
                                              src.create().payload(),
                                              src.create().expected()).path("data.assessment.id");
    // Generate another auth token but same user
    String token = JwtUtil.generateValidToken("developer");

    // Now, attempt to update self-assessment
    AssessmentApi.update(assessmentId, src.payload(), token, src.expected().status())
      .then()
      .body(matchesJsonSchemaInClasspath(src.expected().schema()));
  }

  @Test
  @Tag("Security")
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify userId provided when creating assessment does not pose any risk")
  public void verifyUserIdAttributeDoesNotPoseAnyIntegrityRisk() {
    ApiAssessCreatePojo src = APIBaseDataProvider.load().selfAssessment().leakCreate();
    // This should fail since userId is different from the authenticated user's
    assertSelfAssessing(ADMIN_ID, src.payload(), src.expected());
  }

  @Test(dataProvider = "create422", dataProviderClass = SelfAssessmentDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify self-assessing fails given {desc}")
  public void verifySelfAssessingFailsGivenInvalidParameters(String desc,
                                                             Map<String, Object> payload,
                                                             ApiAssessExpectedPojo expected) {
    assertSelfAssessing(DEVELOPER_ID, payload, expected);
  }
}
