package com.talendradar.data.pojo.api.registration;

import java.util.List;

public record RegUnsuccessfulPojo(List<OnlyAdminRegScenarioPojo> onlyAdmin,
                                  List<InvalidFieldsRegScenarioPojo> invalidFields,
                                  List<InvalidInviteRegScenarioPojo> invalidInvite) {}
