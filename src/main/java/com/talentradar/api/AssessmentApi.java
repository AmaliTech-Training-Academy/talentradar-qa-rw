package com.talentradar.api;

import com.talentradar.specs.RequestSpecs;
import com.talentradar.specs.ResponseSpecs;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

import static io.restassured.RestAssured.given;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssessmentApi {

  public static Response create(Map<String, Object> payload, String authToken, int expectedStatus) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .body(payload)
      .when()
      .post("/assessments")
      .then()
      .spec(ResponseSpecs.defaultSpec(expectedStatus))
      .extract()
      .response();
  }

  public static Response getDimensions(String authToken, int expectedStatus) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .when()
      .get("/dimensions")
      .then()
      .spec(ResponseSpecs.defaultSpec(expectedStatus))
      .extract()
      .response();
  }

  public static Response update(String id, Map<String, Object> payload,
                                String authToken, int expectedStatus) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .pathParam("id", id)
      .body(payload)
      .when()
      .put("/assessments/{id}")
      .then()
      .spec(ResponseSpecs.defaultSpec(expectedStatus))
      .extract()
      .response();
  }

  public static Response getById(String id, String authToken, int expectedStatus) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .pathParam("id", id)
      .when()
      .get("/assessments/{id}")
      .then()
      .spec(ResponseSpecs.defaultSpec(expectedStatus))
      .extract()
      .response();
  }

  public static Response getAll(Map<String, Object> params, String authToken, int expectedStatus) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .queryParams(params)
      .when()
      .get("/assessments")
      .then()
      .spec(ResponseSpecs.defaultSpec(expectedStatus))
      .extract()
      .response();
  }

  public static Response getByUser(Map<String, Object> params, String authToken, int expectedStatus) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .pathParam("userId", params.remove("userId"))
      .queryParams(params)
      .when()
      .get("/users/{userId}/assessments")
      .then()
      .spec(ResponseSpecs.defaultSpec(expectedStatus))
      .extract()
      .response();
  }
}
