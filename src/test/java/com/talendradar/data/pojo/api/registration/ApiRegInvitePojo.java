package com.talendradar.data.pojo.api.registration;

import java.util.Map;

public record ApiRegInvitePojo(Map<String, Object> newUser,
                               ApiRegExpectedPojo expected) {}
