package com.talendradar.data.providers.e2e;

import com.talendradar.data.pojo.E2ERootPojo;
import com.talentradar.utils.YamlLoader;

import java.util.Objects;

public abstract class E2EBaseDataProvider {

  private static E2ERootPojo rootSource;

  public static E2ERootPojo load() {
    if (Objects.isNull(rootSource))
      rootSource = YamlLoader.extract("talentradar-e2e-test-data.yaml", E2ERootPojo.class);
    return rootSource;
  }
}
