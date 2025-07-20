package com.talendradar.data.pojo.api.assessment;

import java.util.Map;

public record ScenarioPojo(String description, Map<String, Object> payload,
                           ApiAssessExpectedPojo expected) {}
