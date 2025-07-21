package com.talentradar.pages.dashboard;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DeveloperDashboard extends BaseDashboard {

  private final Locator selfAssessmentAnchorLocator;

  public DeveloperDashboard(Page page) {
    super(page);
    selfAssessmentAnchorLocator = page.locator("a[href='/dashboard/self-assessment']");
  }

  @Override
  public boolean isReady() {
    try {
      selfAssessmentAnchorLocator.waitFor();
      return true;
    } catch (Exception e) {
      logger.error("DeveloperDashboard readiness check failed: {}", e.getMessage());
      return false;
    }
  }
}
