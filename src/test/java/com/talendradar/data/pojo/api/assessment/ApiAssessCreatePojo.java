package com.talendradar.data.pojo.api.assessment;

import java.util.Map;

public record ApiAssessCreatePojo(Map<String, Object> payload, ApiAssessExpectedPojo expected) {}
