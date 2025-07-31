package com.talentradar.dto;

import com.talentradar.dto.auth.ApiAuthForInactiveOrDeletedUserDto;
import com.talentradar.dto.auth.ApiLoginDto;
import com.talentradar.dto.auth.ApiRoleBasedProtectedDto;
import com.talentradar.dto.registration.ApiRegistrationDto;
import com.talentradar.dto.users.ApiUsersDto;

import java.util.List;

public record ApiRootDto(List<ApiAuthForInactiveOrDeletedUserDto> authForInactiveOrDeletedUser,
                         ApiRoleBasedProtectedDto roleBasedProtected,
                         ApiLoginDto login,
                         ApiRegistrationDto registration,
                         ApiUsersDto users) {}
