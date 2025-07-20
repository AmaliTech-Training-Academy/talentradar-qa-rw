package com.talendradar.data.pojo;

import com.talendradar.data.pojo.api.assessment.ApiAssessPojo;
import com.talendradar.data.pojo.api.login.ApiLoginPojo;
import com.talendradar.data.pojo.api.registration.ApiRegistrationPojo;
import com.talendradar.data.pojo.api.roles.ApiRolesPojo;
import com.talendradar.data.pojo.api.sessions.ApiSessionsPojo;
import com.talendradar.data.pojo.api.users.ApiUsersPojo;

public record ApiRootPojo(ApiLoginPojo login,
                          ApiUsersPojo users,
                          ApiRegistrationPojo registration,
                          ApiRolesPojo roles,
                          ApiSessionsPojo sessions,
                          ApiAssessPojo selfAssessment) {}
