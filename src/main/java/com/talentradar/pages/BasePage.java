package com.talentradar.pages;

import com.microsoft.playwright.Locator;
import com.talentradar.utils.SystemLogger;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;

public abstract class BasePage {

  protected final Page page;
  protected final Logger logger;

  public BasePage(Page page) {
    this.page = page;
    this.logger = SystemLogger.getLogger(getClass());
  }

  public boolean waitForText(String selector, String expectedText) {
    return waitForText(page.locator(selector), expectedText);
  }

  public boolean waitForText(Locator locator, String expectedText) {
    try {
      locator.waitFor();
      return locator.innerText().contains(expectedText);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return false;
    }
  }

  /**
   * Implement in derived classes to define the condition that signifies the page is loaded.
   * Typically, use page.locator(...).waitFor() or assertions.
   */
  public abstract boolean isReady();
}
