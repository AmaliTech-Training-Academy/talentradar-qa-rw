package com.talentradar.api;

import com.talentradar.specs.AuthRequestSpecs;
import com.talentradar.specs.ResponseSpecs;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

import static io.restassured.RestAssured.given;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthApi {

  @Step("Authenticate user")
  public static Response authenticateUser(Map<String, Object> credentials, int expectedStatus) {
    return given()
      .spec(AuthRequestSpecs.defaultSpec())
      .body(credentials)
      .when()
      .post("/auth/login")
      .then()
      .spec(ResponseSpecs.authSpec(expectedStatus))
      .extract()
      .response();
  }
}
