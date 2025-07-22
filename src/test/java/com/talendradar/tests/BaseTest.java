package com.talendradar.tests;

import com.talentradar.utils.EnvUtil;
import com.talentradar.utils.SystemLogger;
import org.slf4j.Logger;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {

  protected static Logger logger;
  protected static String API_BASE_URL,
    JWT_SECRET,
    ADMIN_EMAIL, ADMIN_ID, ADMIN_PASS,
    MANAGER_EMAIL, MANAGER_ID, MANAGER_PASS,
    DEVELOPER_EMAIL, DEVELOPER_ID, DEVELOPER_PASS;

  static {
    API_BASE_URL = EnvUtil.getEnv("API_BASE_URL");
    JWT_SECRET = EnvUtil.getEnv("JWT_SECRET");

    ADMIN_EMAIL = EnvUtil.getEnv("ADMIN_EMAIL");
    ADMIN_ID = EnvUtil.getEnv("ADMIN_ID");
    ADMIN_PASS = EnvUtil.getEnv("ADMIN_PASS");

    MANAGER_EMAIL = EnvUtil.getEnv("MANAGER_EMAIL");
    MANAGER_ID = EnvUtil.getEnv("MANAGER_ID");
    MANAGER_PASS = EnvUtil.getEnv("MANAGER_PASS");

    DEVELOPER_EMAIL = EnvUtil.getEnv("DEVELOPER_EMAIL");
    DEVELOPER_ID = EnvUtil.getEnv("DEVELOPER_ID");
    DEVELOPER_PASS = EnvUtil.getEnv("DEVELOPER_PASS");
  };

  @BeforeClass
  public void enableLogging() {
    logger = SystemLogger.getLogger(getClass());
  }
}
