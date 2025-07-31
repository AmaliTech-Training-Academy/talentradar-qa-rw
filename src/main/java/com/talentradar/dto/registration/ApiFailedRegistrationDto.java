package com.talentradar.dto.registration;

import java.util.List;

public record ApiFailedRegistrationDto(List<ApiRegisterAlreadyChangedUser> alreadyChangedUser,
                                       ApiRegistrationValidationDto validation) {}
