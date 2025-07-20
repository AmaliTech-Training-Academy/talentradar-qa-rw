package com.talendradar.data.providers.api;

import com.talendradar.data.pojo.ApiRootPojo;
import com.talentradar.utils.YamlLoader;

import java.util.Objects;

public abstract class APIBaseDataProvider {

  private static ApiRootPojo rootSource;

  public static ApiRootPojo load() {
    if (Objects.isNull(rootSource))
      rootSource = YamlLoader.extract("talentradar-api-test-data.yaml", ApiRootPojo.class);
    return rootSource;
  }
}
