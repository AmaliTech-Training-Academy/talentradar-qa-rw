package com.talentradar.api;

import com.talentradar.specs.RequestSpecs;
import com.talentradar.specs.ResponseSpecs;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

import static io.restassured.RestAssured.given;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationApi {

  public static Response getNotifications(Map<String, Object> params, String authToken, int expectedStatus) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .queryParams(params)
      .when()
      .get("/notifications")
      .then()
      .spec(ResponseSpecs.defaultSpec(expectedStatus))
      .extract()
      .response();
  }

  public static Response updateNotification(String id, String authToken, int expectedStatus) {
    return given()
      .spec(RequestSpecs.withAuthSpec(authToken))
      .pathParam("id", id)
      .when()
      .patch("/notifications/{id}/read")
      .then()
      .extract()
      .response();
  }
}
