package com.talentradar.dto.auth;

import com.talentradar.dto.ApiExpectedResponseDto;
import com.talentradar.dto.AuthClaimsDto;

public record ApiAuthForInactiveOrDeletedUserDto(AuthClaimsDto claims, ApiExpectedResponseDto expected) {}
