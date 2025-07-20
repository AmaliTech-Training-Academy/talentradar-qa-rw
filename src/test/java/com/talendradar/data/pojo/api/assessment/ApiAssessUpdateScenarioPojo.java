package com.talendradar.data.pojo.api.assessment;

import java.util.Map;

public record ApiAssessUpdateScenarioPojo(ApiAssessCreatePojo create,
                                          Map<String, Object> payload,
                                          ApiAssessExpectedPojo expected) {}
