package com.talendradar.providers.e2e;

import com.talentradar.dto.E2eRootDto;
import com.talentradar.util.YamlLoader;

import java.util.Objects;

public abstract class E2eBaseDataProvider {

  private static E2eRootDto rootSource;

  public static E2eRootDto load() {
    if (Objects.isNull(rootSource))
      rootSource = YamlLoader.extract("talentradar-e2e-test-data.yaml", E2eRootDto.class);
    return rootSource;
  }
}
