package com.talendradar.data.pojo.api.login;

import java.util.Map;

public record ScenarioPojo(String description, Map<String, Object> payload, ApiLoginExpectedPojo expected) {}
