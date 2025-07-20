package com.talendradar.data.pojo.api.assessment;

import java.util.List;

public record ApiAssessPojo(ApiAssessCreatePojo create,
                            List<ApiAssessUpdateScenarioPojo> update,
                            ApiAssessUpdateScenarioPojo alreadyComplete,
                            ApiAssess422Pojo unprocessable,
                            ApiAssessCreatePojo leakCreate) {}
