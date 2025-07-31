package com.talendradar.providers.e2e;

import org.testng.annotations.DataProvider;

public class AuthDataProvider extends E2eBaseDataProvider {

  @DataProvider(name = "login-success")
  public static Object[][] loginSuccess() {
    return load().login().success().stream()
      .map(e -> new Object[] { e.tag(), e.credentials() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "login-failure")
  public static Object[][] loginFailure() {
    return load().login().failure().stream()
      .map(e -> new Object[] { e.tag(), e.credentials(), e.expectedMessage() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "login-validation")
  public static Object[][] loginValidation() {
    return load().login().validation().stream()
      .map(e -> new Object[] { e.tag(), e.credentials(), e.expectedMessage() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "login-xss")
  public static Object[][] loginXSS() {
    return load().login().xss().stream()
      .map(e -> new Object[] { e.tag(), e.credentials(), e.expectedMessage() })
      .toArray(Object[][]::new);
  }
}
