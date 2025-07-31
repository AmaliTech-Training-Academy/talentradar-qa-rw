package com.talentradar.util;

import com.talentradar.api.AssessmentApi;
import com.talentradar.api.AuthApi;
import com.talentradar.api.SessionApi;
import com.talentradar.api.UserApi;
import com.talentradar.dto.*;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiUtil {

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

  public static String register(String token, ApiRequestDto payload, ApiExpectedResponseDto expected) {
    return UserApi.registerUser(token, payload.asSendInvite(), expected.getStatus())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.getSchema()))
      .extract()
      .jsonPath()
      .getString("id");
  }

  public static String registerAndGetInvite(ApiSceneDto scene) {
    String id = register(JwtUtil.generateValidToken("admin"), scene.getRequest(), scene.getExpected());
    return JwtUtil.generateValidRegInvite(
      new NewUserInviteClaimsDto(id, scene.getRequest().getEmail(), scene.getRequest().getRoleId())
    );
  }

  public static List<UserResponseDto> getAllUsers() {
    return UserApi.getUsers(JwtUtil.generateValidToken("admin"), 200)
      .then()
      .body(matchesJsonSchemaInClasspath("schemas/users/userList.json"))
      .extract()
      .jsonPath()
      .getList("data.users", UserResponseDto.class);
  }

  public static List<Map<String, Object>> getDimensionsCache(String token) {
    if (Objects.isNull(dimensionsCache))
      dimensionsCache = AssessmentApi.getDimensions(token, 200)
        .then()
        .body(matchesJsonSchemaInClasspath("schemas/assessment/dimensionList.json"))
        .extract()
        .jsonPath()
        .getList("data");
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
