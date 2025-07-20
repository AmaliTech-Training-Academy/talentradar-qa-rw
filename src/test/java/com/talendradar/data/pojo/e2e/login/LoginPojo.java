package com.talendradar.data.pojo.e2e.login;

import java.util.List;

public record LoginPojo(List<ScenarioPojo> success,
                        List<ScenarioPojo> unauthorized,
                        List<ScenarioPojo> validation,
                        List<ScenarioPojo> xss) {}
