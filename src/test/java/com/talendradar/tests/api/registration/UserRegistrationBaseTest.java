package com.talendradar.tests.api.registration;

import com.talendradar.tests.api.APIBaseTest;
import com.talentradar.dto.UserResponseDto;
import com.talentradar.util.ApiUtil;
import com.talentradar.util.PostgresYamlSeeder;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.List;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Epic("TRA-18: User Management & Security")
@Story("TRA-13: As an admin, I want to register users")
@Owner("QA")
@Link(name = "Epic", url = "https://amali-tech.atlassian.net/browse/TRA-18")
@Link(name = "Story", url = "https://amali-tech.atlassian.net/browse/TRA-13")
public abstract class UserRegistrationBaseTest extends APIBaseTest {

  @BeforeMethod
  void seedEssentialData() {
    // Seed all essential data in db
  }

  @AfterMethod
  void resetSeeds() {
    PostgresYamlSeeder.resetSessions();
    PostgresYamlSeeder.resetUsers();
  }

  void compareUserListToEnsureNoUserWasRegistered(Supplier<Response> runnable) {
    // Query original user list
    List<UserResponseDto> originalList = ApiUtil.getAllUsers();

    // Make api call
    runnable.get();

    // Query the latest user list
    List<UserResponseDto> currentList = ApiUtil.getAllUsers();

    // Both lists should equal
    assertThat(originalList, is(equalTo(currentList)));
  }
}
