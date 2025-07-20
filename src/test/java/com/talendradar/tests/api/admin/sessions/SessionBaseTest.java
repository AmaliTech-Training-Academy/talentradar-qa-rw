package com.talendradar.tests.api.admin.sessions;

import com.talendradar.tests.api.APIBaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;

@Epic("TRA-18: User Management & Security")
@Story("TRA-15: As an admin, I want to see and manage user sessions")
@Owner("QA")
@Link(name = "Epic", url = "https://amali-tech.atlassian.net/browse/TRA-18")
@Link(name = "Story", url = "https://amali-tech.atlassian.net/browse/TRA-15")
public abstract class SessionBaseTest extends APIBaseTest {}
