package com.talendradar.data.pojo.api.registration;

import java.util.Map;

public record ApiRegInviteNewUserPojo(Map<String, Object> newUser, ApiRegExpectedPojo expected) {}
