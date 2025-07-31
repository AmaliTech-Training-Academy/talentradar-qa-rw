package com.talendradar.providers.api;

import com.talentradar.dto.ApiRootDto;
import com.talentradar.util.YamlLoader;

import java.util.Objects;

public abstract class APIBaseDataProvider {

  private static ApiRootDto rootSource;

  public static ApiRootDto load() {
    if (Objects.isNull(rootSource))
      rootSource = YamlLoader.extract("talentradar-api-test-data.yaml", ApiRootDto.class);
    return rootSource;
  }
}
