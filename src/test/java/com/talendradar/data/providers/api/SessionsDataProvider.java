package com.talendradar.data.providers.api;

import org.testng.annotations.DataProvider;

public class SessionsDataProvider extends APIBaseDataProvider {

  @DataProvider(name = "unauthorized")
  public static Object[][] unauthorized() {
    return load().sessions().unauthorized().stream()
      .map(ctx -> new Object[] { ctx.role(), ctx.expected() })
      .toArray(Object[][]::new);
  }
}
