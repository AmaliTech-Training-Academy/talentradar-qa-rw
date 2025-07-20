package com.talendradar.tests.api.users.registration;

import com.talendradar.tests.api.APIBaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;

@Epic("TRA-18: User Management & Security")
@Story("TRA-13: As an admin, I want to register users")
@Owner("QA")
@Link(name = "Epic", url = "https://amali-tech.atlassian.net/browse/TRA-18")
@Link(name = "Story", url = "https://amali-tech.atlassian.net/browse/TRA-13")
public abstract class UserRegistrationBaseTest extends APIBaseTest {}
