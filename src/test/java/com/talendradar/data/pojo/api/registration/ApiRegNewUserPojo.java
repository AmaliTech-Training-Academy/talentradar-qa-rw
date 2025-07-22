package com.talendradar.data.pojo.api.registration;

import java.util.Map;

public record ApiRegNewUserPojo(String email, String roleId) {
  public Map<String, Object> newUser() {
    return Map.of("email", email(), "roleId", roleId());
  }
}
