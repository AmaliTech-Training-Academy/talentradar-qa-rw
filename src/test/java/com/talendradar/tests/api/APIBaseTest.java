package com.talendradar.tests.api;

import com.talendradar.tests.BaseTest;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

public abstract class APIBaseTest extends BaseTest {

  @BeforeSuite
  public void setup() {
    // Set base URI for Rest-Assured
    RestAssured.baseURI = API_BASE_URL;
    // Attached allure filter for logging traffic to Rest-Assured
    RestAssured.filters(new AllureRestAssured());
  }

  protected String getDefaultCurrentUserId(String role) {
    switch (role) {
      case "admin" -> { return ADMIN_ID; }
      case "manager" -> { return MANAGER_ID; }
      default -> { return DEVELOPER_ID; }
    }
  }
}

