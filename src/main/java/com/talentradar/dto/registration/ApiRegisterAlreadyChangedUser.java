package com.talentradar.dto.registration;

import com.talentradar.dto.ApiExpectedResponseDto;
import com.talentradar.dto.ApiRequestDto;
import com.talentradar.dto.NewUserInviteClaimsDto;

public record ApiRegisterAlreadyChangedUser(String desc,
                                            NewUserInviteClaimsDto claims,
                                            ApiRequestDto request, ApiExpectedResponseDto expected) {}
