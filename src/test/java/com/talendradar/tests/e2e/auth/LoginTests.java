package com.talendradar.tests.e2e.auth;

import com.microsoft.playwright.Page;
import com.talendradar.data.pojo.e2e.login.CredentialsPojo;
import com.talendradar.data.providers.e2e.LoginDataProvider;
import com.talendradar.tests.e2e.BaseE2ETest;
import com.talentradar.pages.LoginPage;
import com.talentradar.utils.spy.NetworkRequestSpy;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.annotations.*;
import org.testng.Assert;

import java.util.Set;

public class LoginTests extends BaseE2ETest {

  private LoginPage page;

  @BeforeMethod
  public void initialize() {
    navigateTo("/login");
    page = new LoginPage(getPage());
  }

  @Test(dataProvider = "success", dataProviderClass = LoginDataProvider.class)
  @Severity(SeverityLevel.BLOCKER)
  @Description("Verify {role} can login")
  public void verifyUserCanLogin(String role, CredentialsPojo credentials) {
    Assert.assertTrue(page.isReady());
    Assert.assertTrue(page
      .login(credentials.email(), credentials.password(), role)
      .isReady()
    );
  }

  @Test(dataProvider = "unauthorized", dataProviderClass = LoginDataProvider.class)
  @Severity(SeverityLevel.BLOCKER)
  @Description("Verify login fails with {desc}")
  public void verifyLoginIsUnauthorizedGivenInvalidCredentials(String desc,
                                                               CredentialsPojo credentials,
                                                               String expectedMessage) {
    Assert.assertTrue(page.isReady());
    page.login(credentials.email(), credentials.password(), "developer");
    Assert.assertTrue(page.waitForText("p", expectedMessage));
  }

  @Test(dataProvider = "validation", dataProviderClass = LoginDataProvider.class)
  @Severity(SeverityLevel.BLOCKER)
  @Description("Verify immediate validation given {desc}")
  public void verifyUIValidationReliability(String desc, CredentialsPojo credentials, String expectedMessage) {
    Assert.assertTrue(page.isReady());

    Set<String> requests = NetworkRequestSpy.spy(getPage(), () ->
      page.login(credentials.email(), credentials.password(), "developer")
    );

    boolean requestWasMade = requests.stream().anyMatch(url -> url.contains("/api/v1/auth/login"));
    if (requestWasMade)
      throw new AssertionError("Unwanted API call detected");

    Assert.assertTrue(page.waitForText("p", expectedMessage));
  }

  @Test(dataProvider = "xss", dataProviderClass = LoginDataProvider.class)
  @Severity(SeverityLevel.BLOCKER)
  @Tag("Security")
  @Description("Verify password field is not susceptible to xss attacks")
  public void verifyPasswordIsNotSusceptibleToXSSAttacks(String desc, CredentialsPojo credentials, String value) {
    Assert.assertTrue(page.isReady());

    Page playwrightPage = getPage();

    // Check pre-XSS state
    Boolean executed = (Boolean) playwrightPage.evaluate("() => { window.x = 1; return window.x === 1; }");
    Assert.assertEquals(executed, Boolean.TRUE);

    // Reset flag
    playwrightPage.evaluate("() => { window.x = 0; }");

    // Attempt login with XSS payload
    page.login(credentials.email(), credentials.password(), "developer");

    // Confirm failure UI is shown
    Assert.assertTrue(page.waitForText("div", "Failure"));

    // Check if XSS payload executed
    Boolean xssTriggered = (Boolean) playwrightPage.evaluate(String.format("() => window.x === %s", value));
    Assert.assertFalse(xssTriggered);
  }
}
