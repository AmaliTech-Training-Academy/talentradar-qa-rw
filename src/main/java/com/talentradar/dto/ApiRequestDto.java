package com.talentradar.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
public class ApiRequestDto {
  // Role
  private String roleId;
  private String role;

  // User info
  private String fullName;

  // Login credentials payload
  private String email;
  private String password;
  private String confirmPassword;

  public Map<String, Object> asLogin() {
    return refineMapPayload(Map.of("email", getEmail(),
                                   "password", getPassword()));
  }

  public Map<String, Object> asAcceptInvite() {
    return refineMapPayload(Map.of("fullName", getFullName(),
                                   "password", getPassword(),
                                   "confirmPassword", getConfirmPassword()));
  }

  public Map<String, Object> asSendInvite() {
    return refineMapPayload(Map.of("email", getEmail(),
                                   "roleId", getRoleId()));
  }

  public NewUserInviteClaimsDto asNewUserInviteClaims(String id) {
    return new NewUserInviteClaimsDto(id, getEmail(), getRoleId());
  }

  private Map<String, Object> refineMapPayload(Map<String, Object> rawPayload) {
    Map<String, Object> refined = new HashMap<>(rawPayload);
    refined.values().removeIf(Objects::isNull);
    return refined;
  }
}
