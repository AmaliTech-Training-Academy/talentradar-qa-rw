package com.talendradar.data.providers.api;

import org.testng.annotations.DataProvider;

public class UsersDataProvider extends APIBaseDataProvider {

  @DataProvider(name = "allOnlyAdmin")
  public static Object[][] allOnlyAdmin() {
    return load().users().allOnlyAdmin().stream()
      .map(ctx -> new Object[] { ctx.role(), ctx.expected() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "currentUser")
  public static Object[][] currentUser() {
    return load().users().currentUser().stream()
      .map(ctx -> new Object[] { ctx.role(), ctx.expected() })
      .toArray(Object[][]::new);
  }
}
