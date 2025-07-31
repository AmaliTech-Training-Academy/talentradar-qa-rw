package com.talentradar.dto.auth;

import com.talentradar.dto.ApiSceneDto;

import java.util.List;

public record ApiRoleBasedProtectedDto(List<ApiSceneDto> admin,
                                       List<ApiSceneDto> manager, List<ApiSceneDto> developer) {}
