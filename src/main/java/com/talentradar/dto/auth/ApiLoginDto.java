package com.talentradar.dto.auth;

import com.talentradar.dto.ApiDescribedSceneDto;

import java.util.List;

public record ApiLoginDto(List<ApiDescribedSceneDto> succeeded, List<ApiDescribedSceneDto> failed) {}
