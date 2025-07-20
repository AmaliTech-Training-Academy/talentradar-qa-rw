package com.talentradar.api;

import com.talentradar.specs.AuthRequestSpecs;
import com.talentradar.specs.RequestSpecs;
import com.talentradar.specs.ResponseSpecs;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

import static io.restassured.RestAssured.given;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserApi {

  public static Response getUsers(String authToken, int status) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .when()
      .get("/users")
      .then()
      .spec(ResponseSpecs.defaultSpec(status))
      .extract()
      .response();
  }

  public static Response getCurrentUserDetails(String authToken, int status) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .when()
      .get("/users/me")
      .then()
      .spec(ResponseSpecs.defaultSpec(status))
      .extract()
      .response();
  }

  public static Response registerUser(String authToken, Map<String, Object> payload, int status) {
    return given()
      .spec(AuthRequestSpecs.withAuthSpec(authToken))
      .body(payload)
      .when()
      .post("/invite")
      .then()
      .spec(ResponseSpecs.defaultSpec(status))
      .extract()
      .response();
  }

  public static Response completeRegistration(String invitation, Map<String, Object> payload, int status) {
    return given()
      .spec(AuthRequestSpecs.defaultSpec())
      .queryParam("token", invitation)
      .body(payload)
      .when()
      .patch("/complete-registration")
      .then()
      .spec(ResponseSpecs.defaultSpec(status))
      .extract()
      .response();
  }
}
