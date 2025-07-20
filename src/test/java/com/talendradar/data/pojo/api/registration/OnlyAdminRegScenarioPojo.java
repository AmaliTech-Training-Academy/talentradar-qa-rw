package com.talendradar.data.pojo.api.registration;

import java.util.Map;

public record OnlyAdminRegScenarioPojo(String role,
                                       Map<String, Object> newUser,
                                       ApiRegExpectedPojo expected) {}
