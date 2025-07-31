package com.talendradar.providers.api;

import org.testng.annotations.DataProvider;

public class AuthDataProvider extends APIBaseDataProvider {

  @DataProvider(name = "login-success")
  public static Object[][] successfulLogin() {
    return load().login().succeeded().stream()
      .map(e -> new Object[]{ e.getDesc(), e.getRequest(), e.getExpected() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "login-failure")
  public static Object[][] unsuccessfulLogin() {
    return load().login().failed().stream()
      .map(e -> new Object[]{ e.getDesc(), e.getRequest(), e.getExpected() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "auth-for-inactive-or-deleted-user")
  public static Object[][] authForInactiveOrDeletedUser() {
    return load().authForInactiveOrDeletedUser().stream()
      .map(e -> new Object[]{ e.claims(), e.expected() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "any-role-accessible")
  public static Object[][] anyRoleAccessible() {
    return new Object[][] { { "admin" }, { "manager" }, { "developer" } };
  }

  @DataProvider(name = "admin-role-based-protected")
  public static Object[][] adminProtected() {
    return load().roleBasedProtected().admin().stream()
      .map(e -> new Object[]{ e.getRequest(), e.getExpected() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "manager-role-based-protected")
  public static Object[][] managerProtected() {
    return load().roleBasedProtected().manager().stream()
      .map(e -> new Object[]{ e.getRequest(), e.getExpected() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "developer-role-based-protected")
  public static Object[][] developerProtected() {
    return load().roleBasedProtected().developer().stream()
      .map(e -> new Object[]{ e.getRequest(), e.getExpected() })
      .toArray(Object[][]::new);
  }
}
