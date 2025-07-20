package com.talendradar.data.providers.api;

import org.testng.annotations.DataProvider;

public class RolesDataProvider extends APIBaseDataProvider {

  @DataProvider(name = "onlyAdminCanQueryAllRoles")
  public static Object[][] onlyAdminCanQueryAllRoles() {
    return load().roles().onlyAdminCanQueryAllRoles().stream()
      .map(ctx -> new Object[] { ctx.role(), ctx.expected() })
      .toArray(Object[][]::new);
  }
}
