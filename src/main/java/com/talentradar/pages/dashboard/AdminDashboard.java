package com.talentradar.pages.dashboard;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AdminDashboard extends BaseDashboard {

  private final Locator securityAnchorLocator;

  public AdminDashboard(Page page) {
    super(page);
    securityAnchorLocator = page.locator("a[href='/dashboard/security']");
  }

  @Override
  public boolean isReady() {
    try {
      securityAnchorLocator.waitFor();
      return true;
    } catch (Exception e) {
      logger.error("AdminDashboard readiness check failed: {}", e.getMessage());
      return false;
    }
  }
}
