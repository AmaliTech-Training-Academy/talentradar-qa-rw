package com.talendradar.tests.api.auth;

import com.talendradar.tests.api.APIBaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;

@Epic("TRA-18: User Management & Security")
@Story("TRA-1: As a User, I want to be able to log in")
@Tag("Scenario: Verify user credentials and generate JWT")
@Owner("QA")
@Link(name = "Epic", url = "https://amali-tech.atlassian.net/browse/TRA-18")
@Link(name = "Story", url = "https://amali-tech.atlassian.net/browse/TRA-1")
@Link(name = "Subtask", url = "https://amali-tech.atlassian.net/browse/TRA-9")
public abstract class AuthBaseTest extends APIBaseTest {}
