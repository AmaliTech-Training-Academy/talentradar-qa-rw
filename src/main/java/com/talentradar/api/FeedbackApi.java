package com.talentradar.api;

import com.talentradar.specs.RequestSpecs;
import com.talentradar.specs.ResponseSpecs;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

import static io.restassured.RestAssured.given;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackApi {

  public static Response getAssignedDevelopers(Map<String, Object> params, String authToken, int expectedStatus) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .pathParam("id", params.remove("managerId"))
      .queryParams(params)
      .when()
      .get("/manager/{id}/developers")
      .then()
      .spec(ResponseSpecs.defaultSpec(expectedStatus))
      .extract()
      .response();
  }

  public static Response createFeedback(Map<String, Object> payload, String authToken, int expectedStatus) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .body(payload)
      .when()
      .post("/feedback")
      .then()
      .extract()
      .response();
  }

  public static Response getFeedbackHistory(Map<String, Object> params, String authToken, int expectedStatus) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .pathParam("developerId", params.remove("developerId"))
      .queryParams(params)
      .when()
      .get("/feedback/developer/{developerId}")
      .then()
      .spec(ResponseSpecs.defaultSpec(expectedStatus))
      .extract()
      .response();
  }

  public static Response getFeedbackById(String id, String authToken, int expectedStatus) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .pathParam("id", id)
      .when()
      .get("/feedback/{id}")
      .then()
      .spec(ResponseSpecs.defaultSpec(expectedStatus))
      .extract()
      .response();
  }
}
