package com.talendradar.data.providers.api;

import org.testng.annotations.DataProvider;

public class LoginDataProvider extends APIBaseDataProvider {

  @DataProvider(name = "success")
  public static Object[][] success() {
    return load().login().success().stream()
      .map(tc -> new Object[]{ tc.description(), tc.payload(), tc.expected() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "unauthorized")
  public static Object[][] unauthorized() {
    return load().login().unauthorized().stream()
      .map(tc -> new Object[]{ tc.description(), tc.payload(), tc.expected() })
      .toArray(Object[][]::new);
  }
}
