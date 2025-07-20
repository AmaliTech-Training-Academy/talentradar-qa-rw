package com.talentradar.utils;

import com.talentradar.api.AssessmentApi;
import com.talentradar.api.AuthApi;
import com.talentradar.api.SessionApi;
import com.talentradar.api.UserApi;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class APIUtil {

  private static List<Map<String, Object>> dimensionsCache;

  public static String login(Map<String, Object> credentials) {
    Response response = AuthApi.authenticateUser(credentials, 200);
    response.then()
      .body(matchesJsonSchemaInClasspath("schemas/auth/loginSuccess.json"));
    return response.getCookie("token");
  }

  public static Object getSessionId(String token, String userId) {
    return SessionApi.getSessions(token, 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/admin/sessions/sessionList.json"))
      .extract()
      .jsonPath()
      .getMap("data.find { it.user_id == '" + userId + "' }")
      .get("id");
  }

  public static String register(String token, Map<String, Object> newUser, int status, String schema) {
    return UserApi.registerUser(token, newUser, status)
      .then()
      .body(matchesJsonSchemaInClasspath(schema))
      .extract()
      .path("invitation");
  }

  public static List<Map<String, Object>> getDimensionsCache(String token) {
    if (Objects.isNull(dimensionsCache))
      dimensionsCache = AssessmentApi.getDimensions(token, 200)
        .then()
        .body(matchesJsonSchemaInClasspath("schemas/assessment/dimensionList.json"))
        .extract()
        .path("data" );
    return dimensionsCache;
  }

  public static void refineSelfAssessmentPayload(String token, Map<String, Object> payload) {
    @SuppressWarnings("unchecked")
    Map<String, Object> snap = (Map<String, Object>) payload.get("dimensions");

    payload.put(
      "dimensions",
      getDimensionsCache(token).stream()
        .filter(it -> snap.containsKey((String) it.get("dimension_name")))
        .map(it -> Map.of("dimension_definition_id", it.get("id"),
                                           "rating", snap.get((String) it.get("dimension_name"))))
        .collect(Collectors.toList()));
  }
}
