package com.talentradar.pages.dashboard;

import com.talentradar.pages.BasePage;
import com.microsoft.playwright.Page;

public abstract class BaseDashboard extends BasePage {
  public BaseDashboard(Page page) {
    super(page);
  }

  @Override
  public boolean isReady() {
    return true;
  }
}
