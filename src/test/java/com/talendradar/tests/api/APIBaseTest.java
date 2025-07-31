package com.talendradar.tests.api;

import com.talendradar.tests.BaseTest;
import com.talentradar.util.Envs;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

public abstract class APIBaseTest extends BaseTest {

  @BeforeSuite
  public void setup() {
    // Set base URI for Rest-Assured
    RestAssured.baseURI = Envs.API_BASE_URL;
    RestAssured.basePath = Envs.API_BASE_PATH;
    // Attached allure filter for logging traffic to Rest-Assured
    RestAssured.filters(new AllureRestAssured());
  }

  protected String getDefaultCurrentUserId(String role) {
    switch (role) {
      case "admin" -> { return Envs.ADMIN_ID; }
      case "manager" -> { return Envs.MANAGER_ID; }
      default -> { return Envs.DEVELOPER_ID; }
    }
  }
}

