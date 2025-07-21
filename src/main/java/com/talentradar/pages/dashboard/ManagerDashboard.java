package com.talentradar.pages.dashboard;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ManagerDashboard extends BaseDashboard {

  private final Locator managerFeedbackAnchorLocator;

  public ManagerDashboard(Page page) {
    super(page);
    managerFeedbackAnchorLocator = page.locator("a[href='/dashboard/manager-feedback']");
  }

  @Override
  public boolean isReady() {
    try {
      managerFeedbackAnchorLocator.waitFor();
      return true;
    } catch (Exception e) {
      logger.error("ManagerDashboard readiness check failed: {}", e.getMessage());
      return false;
    }
  }
}
