package com.talendradar.data.providers.api;

import org.testng.annotations.DataProvider;

public class RegistrationDataProvider extends APIBaseDataProvider {

  @DataProvider(name = "invite")
  public static Object[][] invite() {
    return new Object[][] {
      {
        load().registration().success().invite().newUser(),
        load().registration().success().invite().expected()
      }
    };
  }

  @DataProvider(name = "completeRegistration")
  public static Object[][] completeRegistration() {
    return new Object[][] {
      {
        load().registration().success().completeRegistration().invite(),
        load().registration().success().completeRegistration().payload(),
        load().registration().success().completeRegistration().expected()
      }
    };
  }

  @DataProvider(name = "onlyAdminCanInviteUser")
  public static Object[][] onlyAdminCanInviteUser() {
    return load().registration().unsuccessful().onlyAdmin().stream()
      .map(tc -> new Object[] { tc.role(), tc.newUser(), tc.expected() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "invalidFields")
  public static Object[][] invalidFields() {
    return load().registration().unsuccessful().invalidFields().stream()
      .map(tc -> new Object[] { tc.description(), tc.newUser(), tc.expected() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "invalidInvite")
  public static Object[][] invalidInvite() {
    return load().registration().unsuccessful().invalidInvite().stream()
      .map(tc -> new Object[] { tc.description(), tc.token(), tc.expected() })
      .toArray(Object[][]::new);
  }
}
