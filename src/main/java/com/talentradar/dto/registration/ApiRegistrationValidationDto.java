package com.talentradar.dto.registration;

import com.talentradar.dto.ApiDescribedSceneDto;

import java.util.List;

public record ApiRegistrationValidationDto(List<ApiDescribedSceneDto> sendInvite,
                                           List<ApiAcceptInviteValidationSceneDto> acceptInvite,
                                           List<ApiAcceptInviteSceneDto> expiredInvite) {}
