package com.talendradar.data.providers.e2e;

import org.testng.annotations.DataProvider;

public class LoginDataProvider extends E2EBaseDataProvider {

  @DataProvider(name = "success")
  public static Object[][] success() {
    return load().login().success().stream()
      .map(cx -> new Object[] { cx.tag(), cx.credentials() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "unauthorized")
  public static Object[][] unauthorized() {
    return load().login().unauthorized().stream()
      .map(cx -> new Object[] { cx.tag(), cx.credentials(), cx.expectedMessage() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "validation")
  public static Object[][] validation() {
    return load().login().validation().stream()
      .map(cx -> new Object[] { cx.tag(), cx.credentials(), cx.expectedMessage() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "xss")
  public static Object[][] xss() {
    return load().login().xss().stream()
      .map(cx -> new Object[] { cx.tag(), cx.credentials(), cx.expectedMessage() })
      .toArray(Object[][]::new);
  }
}
