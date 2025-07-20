package com.talentradar.pages;

import com.talentradar.pages.dashboard.AdminDashboard;
import com.talentradar.pages.dashboard.BaseDashboard;
import com.talentradar.pages.dashboard.DeveloperDashboard;
import com.talentradar.pages.dashboard.ManagerDashboard;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {

  private final Locator usernameOrEmailLocator;
  private final Locator passwordLocator;
  private final Locator loginLocator;

  public LoginPage(Page page) {
    super(page);
    this.usernameOrEmailLocator = page.locator("#loginEmail");
    this.passwordLocator = page.locator("#loginPassword");
    this.loginLocator = page.locator("button:text('Login')");
  }

  @Override
  public boolean isReady() {
    try {
      usernameOrEmailLocator.waitFor();
      passwordLocator.waitFor();
      loginLocator.waitFor();
      return true;
    } catch (Exception e) {
      logger.error("LoginPage readiness check failed: {}", e.getMessage());
      return false;
    }
  }

  @Step("Enter username or email: {usernameOrEmail}")
  private void enterUsernameOrEmail(String usernameOrEmail) {
    usernameOrEmailLocator.fill(usernameOrEmail);
  }

  @Step("Enter password: ****")
  private void enterPassword(String password) {
    passwordLocator.fill(password);
  }

  @Step("Click `Login` button")
  public void clickLogin() {
    loginLocator.click();
  }

  public BaseDashboard login(String email, String password, String role) {
    enterUsernameOrEmail(email);
    enterPassword(password);
    clickLogin();

    return switch (role.toLowerCase()) {
      case "developer" -> new DeveloperDashboard(page);
      case "manager" -> new ManagerDashboard(page);
      default -> new AdminDashboard(page);
    };
  }
}
