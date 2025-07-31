package com.talentradar.dto.registration;

import com.talentradar.dto.ApiExpectedResponseDto;
import com.talentradar.dto.ApiRequestDto;
import com.talentradar.dto.ApiSceneDto;

public record ApiAcceptInviteValidationSceneDto(String desc, ApiSceneDto sendInvite,
                                                ApiRequestDto request, ApiExpectedResponseDto expected) {}
