package com.talendradar.data.pojo.api.registration;

import java.util.Map;

public record ApiCompleteRegPojo(ApiRegInvitePojo invite,
                                 Map<String, Object> payload,
                                 ApiRegExpectedPojo expected) {}
