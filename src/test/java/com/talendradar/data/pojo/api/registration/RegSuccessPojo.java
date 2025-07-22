package com.talendradar.data.pojo.api.registration;

import java.util.List;

public record RegSuccessPojo(List<ApiRegInvitePojo> invite, ApiCompleteRegPojo completeRegistration) {}
