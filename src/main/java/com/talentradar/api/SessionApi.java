package com.talentradar.api;

import com.talentradar.specs.RequestSpecs;
import com.talentradar.specs.ResponseSpecs;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

import static io.restassured.RestAssured.given;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionApi {

  public static Response getSessions(String token, int status) {
    return given()
      .spec(RequestSpecs.withAuthSpec(token))
      .when()
      .get("/admin/sessions")
      .then()
      .spec(ResponseSpecs.defaultSpec(status))
      .extract()
      .response();
  }

  public static Response filterSessions(String token, Map<String, Object> params, int status) {
    return given()
      .spec(RequestSpecs.withAuthSpec(token))
      .queryParams(params)
      .when()
      .get("/admin/sessions")
      .then()
      .spec(ResponseSpecs.defaultSpec(status))
      .extract()
      .response();
  }

  public static Response revokeSession(String id, String token, int status) {
    return given()
      .spec(RequestSpecs.withAuthSpec(token))
      .pathParam("id", id)
      .when()
      .delete("/admin/sessions/{id}")
      .then()
      .spec(ResponseSpecs.defaultSpec(status))
      .extract()
      .response();
  }
}
