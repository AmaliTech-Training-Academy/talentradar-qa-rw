package com.talendradar.data.pojo.api.login;

import java.util.List;

public record ApiLoginPojo(List<ScenarioPojo> success, List<ScenarioPojo> unauthorized) {}
