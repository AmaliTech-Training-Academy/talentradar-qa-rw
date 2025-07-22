package com.talendradar.tests.e2e;

import com.talendradar.tests.BaseTest;
import com.talentradar.utils.EnvUtil;

import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

import io.qameta.allure.Step;
import io.qameta.allure.testng.Tag;
import org.testng.ITestResult;
import org.testng.annotations.*;

@Tag("Project: TalentRadar.ai")
public class BaseE2ETest extends BaseTest {

  private static final ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
  private static final ThreadLocal<Browser> browserThread = new ThreadLocal<>();
  private static final ThreadLocal<BrowserContext> contextThread = new ThreadLocal<>();
  private static final ThreadLocal<Page> pageThread = new ThreadLocal<>();

  protected String BASE_URI;

  @BeforeClass
  @Parameters("browser")
  public void startup(@Optional("chromium") String browserName) {
    BASE_URI = EnvUtil.getEnv("E2E_BASE_URL");

    Playwright playwright = Playwright.create();
    Browser browser = switch (browserName.toLowerCase()) {
      case "firefox" -> playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true));
      case "webkit" -> playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(true));
      default -> playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    };

    playwrightThread.set(playwright);
    browserThread.set(browser);

    logger.info("Browser [{}] launched once for class", browserName);
  }

  @BeforeMethod
  public void setup() {
    Browser browser = getBrowser();
    BrowserContext context = browser.newContext(new Browser.NewContextOptions()
      .setBaseURL(BASE_URI)
    );
    context.setDefaultTimeout(10_000);

    Page page = context.newPage();

    contextThread.set(context);
    pageThread.set(page);
  }

  @BeforeMethod(dependsOnMethods = "setup")
  public void attachPageToTestResult(ITestResult result) {
    result.setAttribute("playwright.page", getPage());
  }

  @Step("Go to {url}")
  public void navigateTo(String url) {
    getPage().navigate(url);
  }

  @AfterMethod
  public void teardown() {
    BrowserContext context = getContext();

    if (context != null) context.close();

    pageThread.remove();
    contextThread.remove();
  }

  @AfterClass
  public void cleanup() {
    Browser browser = getBrowser();
    Playwright playwright = getPlaywright();

    if (browser != null) browser.close();
    if (playwright != null) playwright.close();

    browserThread.remove();
    playwrightThread.remove();

    logger.info("Browser and Playwright closed after class");
  }

  protected Page getPage() {
    return pageThread.get();
  }

  protected BrowserContext getContext() {
    return contextThread.get();
  }

  protected Browser getBrowser() {
    return browserThread.get();
  }

  protected Playwright getPlaywright() {
    return playwrightThread.get();
  }
}
