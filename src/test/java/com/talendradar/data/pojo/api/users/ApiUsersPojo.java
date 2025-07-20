package com.talendradar.data.pojo.api.users;

import java.util.List;

public record ApiUsersPojo(List<ApiUsersOnlyAdminScenarioPojo> allOnlyAdmin,
                           List<ApiMeScenarioPojo> currentUser) {}
