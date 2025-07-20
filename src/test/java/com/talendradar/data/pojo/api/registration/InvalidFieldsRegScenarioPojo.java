package com.talendradar.data.pojo.api.registration;

import java.util.Map;

public record InvalidFieldsRegScenarioPojo(String description,
                                           Map<String, Object> newUser,
                                           ApiRegExpectedPojo expected) {}
