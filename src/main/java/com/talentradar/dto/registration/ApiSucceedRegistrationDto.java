package com.talentradar.dto.registration;

import com.talentradar.dto.ApiSceneDto;

import java.util.List;

public record ApiSucceedRegistrationDto(List<ApiSceneDto> sendInvite, List<ApiAcceptInviteSceneDto> acceptInvite) {}
