package com.talendradar.listeners;

import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class AllureSuccessAndFailureTestListener implements ITestListener {

  @Override
  public void onTestSuccess(ITestResult result) {
    System.out.println("✓ Test PASSED: " + result.getMethod().getMethodName());
    addEnvironmentInfo();
  }

  @Override
  public void onTestFailure(ITestResult result) {
    System.out.println("✗ Test FAILED: " + result.getMethod().getMethodName());
    addEnvironmentInfo();
  }

  private void addEnvironmentInfo() {
    Allure.parameter("Environment", System.getProperty("env", "test"));
    Allure.parameter("OS", System.getProperty("os.name"));
    Allure.parameter("Java Version", System.getProperty("java.version"));
    Allure.parameter("Timestamp", String.valueOf(System.currentTimeMillis()));
  }
}
