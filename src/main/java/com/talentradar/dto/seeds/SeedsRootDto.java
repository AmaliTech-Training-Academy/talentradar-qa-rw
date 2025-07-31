package com.talentradar.dto.seeds;

import java.util.List;

public record SeedsRootDto(List<UserSeedDto> users, List<RoleSeedDto> roles) {}
