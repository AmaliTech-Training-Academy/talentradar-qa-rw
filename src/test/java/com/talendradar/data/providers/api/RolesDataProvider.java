package com.talendradar.data.providers.api;

import org.testng.annotations.DataProvider;

public class RolesDataProvider extends APIBaseDataProvider {

  @DataProvider(name = "onlyAdminCanQueryAllRoles")
  public static Object[][] onlyAdminCanQueryAllRoles() {
    return load().roles().onlyAdminCanQueryAllRoles().stream()
      .map(ctx -> new Object[] { ctx.role(), ctx.expected() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "role-labels")
  public static Object[][] roleLabes() {
    return new Object[][] {{ "admin" }, { "manager" }, { "developer" }};
  }
}
