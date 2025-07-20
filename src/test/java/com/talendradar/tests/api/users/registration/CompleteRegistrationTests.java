package com.talendradar.tests.api.users.registration;

import com.talendradar.data.pojo.api.registration.ApiRegExpectedPojo;
import com.talendradar.data.pojo.api.registration.ApiRegInvitePojo;
import com.talendradar.data.providers.api.RegistrationDataProvider;
import com.talentradar.api.UserApi;
import com.talentradar.utils.APIUtil;
import com.talentradar.utils.JwtUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CompleteRegistrationTests extends UserRegistrationBaseTest {

  @Test(dataProvider = "completeRegistration", dataProviderClass = RegistrationDataProvider.class)
  @Severity(SeverityLevel.CRITICAL)
  @Description("Verify user registration full flow")
  public void verifyUserRegistrationJourney(ApiRegInvitePojo invite,
                                            Map<String, Object> payload,
                                            ApiRegExpectedPojo expected) {
    // Register new user
    String invitation = APIUtil.register(JwtUtil.generateValidToken("admin"),
                                         invite.newUser(), invite.expected().status(),
                                         invite.expected().schema());

    // Complete accept registration invite
    UserApi.completeRegistration(invitation, payload, expected.status())
      .then()
      .body(matchesJsonSchemaInClasspath(expected.schema()));
  }
}
