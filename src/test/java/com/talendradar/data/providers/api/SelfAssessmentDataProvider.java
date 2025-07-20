package com.talendradar.data.providers.api;

import org.testng.annotations.DataProvider;

public class SelfAssessmentDataProvider extends APIBaseDataProvider {

  @DataProvider(name = "onlyDeveloper")
  public static Object[][] onlyDeveloper() {
    return new Object[][] {{ "admin", 403 }, { "manager", 403 }};
  }

  @DataProvider(name = "update")
  public static Object[][] updateAsDraft() {
    return load().selfAssessment().update().stream()
      .map(tc -> new Object[] { tc.create(), tc.payload(), tc.expected() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "create422")
  public static Object[][] create422() {
    return load().selfAssessment().unprocessable().create().stream()
      .map(tc -> new Object[] { tc.description(), tc.payload(), tc.expected() })
      .toArray(Object[][]::new);
  }
}
