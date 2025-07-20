package com.talentradar.api;

import com.talentradar.specs.RequestSpecs;
import com.talentradar.specs.ResponseSpecs;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static io.restassured.RestAssured.given;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RolesApi {

  public static Response getRoles(String token, int status) {
    return given()
      .spec(RequestSpecs.withAuthSpec(token))
      .when()
      .get("/roles")
      .then()
      .spec(ResponseSpecs.defaultSpec(status))
      .extract()
      .response();
  }
}
