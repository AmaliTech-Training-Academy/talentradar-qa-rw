package com.talendradar.data.pojo.api.roles;

import java.util.List;

public record ApiRolesPojo(List<ApiRolesScenePojo> onlyAdminCanQueryAllRoles) {}
