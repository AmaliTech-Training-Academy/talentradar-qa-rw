package com.talendradar.providers.api;

import org.testng.annotations.DataProvider;

public class UserDataProvider extends APIBaseDataProvider {

  @DataProvider(name = "current-user")
  public static Object[][] currentUser() {
    return load().users().currentUser().stream()
      .map(e -> new Object[] { e.getRequest(), e.getExpected() })
      .toArray(Object[][]::new);
  }
}
