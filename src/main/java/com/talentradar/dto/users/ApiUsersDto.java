package com.talentradar.dto.users;

import com.talentradar.dto.ApiSceneDto;

import java.util.List;

public record ApiUsersDto(List<ApiSceneDto> currentUser) {}
