package com.talendradar.tests.api.assessment;

import com.talendradar.tests.api.APIBaseTest;
import com.talentradar.api.AssessmentApi;
import com.talentradar.dto.ApiExpectedResponseDto;
import com.talentradar.util.ApiUtil;
import com.talentradar.util.JwtUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("TRA-23: Productivity Scorecard & AI-Driven Guidance")
@Story("TRA-17: Developer Self-Assessment")
@Link(name = "Epic", url = "https://amali-tech.atlassian.net/browse/TRA-23")
@Link(name = "Story", url = "https://amali-tech.atlassian.net/browse/TRA-17")
public abstract class AssessmentBaseTest extends APIBaseTest {

  protected ExtractableResponse<Response> assertSelfAssessing(String userId,
                                                              Map<String, Object> payload,
                                                              ApiExpectedResponseDto expected) {
    // Generate auth token
    String token = JwtUtil.generateValidToken("developer");

    // Fetch dimensions via API and aggregate the dimensions in payload
    ApiUtil.refineSelfAssessmentPayload(token, payload);

    // Add userId to the payload
    payload.put("userId", userId);

    // Now, attempt to create self-assessment
    return AssessmentApi.create(payload, token, expected.getStatus())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.getSchema()))
      .extract();
  }
}
