package com.talendradar.tests;

import com.talentradar.util.PostgresYamlSeeder;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest {

  @BeforeSuite
  public void seedDB() {
    PostgresYamlSeeder.seed();
  }

  @AfterSuite
  public void purgeDB() {
    PostgresYamlSeeder.purge();
    PostgresYamlSeeder.disconnect();
  }
}
