package com.talendradar.data.providers.api;

import com.talendradar.data.pojo.api.registration.ApiRegExpectedPojo;
import com.talendradar.data.pojo.api.registration.ApiRegInviteNewUserPojo;
import com.talendradar.data.pojo.api.registration.ApiRegInvitePojo;
import com.talendradar.data.pojo.api.registration.ApiRegNewUserPojo;
import com.talentradar.pojo.NewUserDetails;
import com.talentradar.utils.EnvUtil;
import com.talentradar.utils.JwtUtil;
import org.testng.annotations.DataProvider;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationDataProvider extends APIBaseDataProvider {

  private static String dynamicEmail() {
    try {
      Thread.sleep(2L);
    } catch (InterruptedException ignored) {}
    return "testUser_" + System.currentTimeMillis() + "@example.com";
  }

  private static Map<String, Object> aggregateNewUserEMail(Map<String, Object> newUser) {
    String email = (String) newUser.get("email");
    if (Objects.isNull(email))
      return newUser;

    int atIndex = email.indexOf("@");
    email = email.substring(0, atIndex) + System.currentTimeMillis() + email.substring(atIndex);
    Map<String, Object> copyOfNewUser = new HashMap<>(newUser);
    copyOfNewUser.put("email", email);

    return copyOfNewUser;
  }

  @DataProvider(name = "invite")
  public static Object[][] invite() {
    return load().registration().success().invite().stream()
      .map(ctx -> new Object[] {
        new ApiRegNewUserPojo(dynamicEmail(), ctx.roleId()).newUser(),
        ctx.expected()
      }).toArray(Object[][]::new);
  }

  @DataProvider(name = "completeRegistration")
  public static Object[][] completeRegistration() {
    ApiRegInvitePojo invite = load().registration()
      .success()
      .completeRegistration()
      .invite();
    return new Object[][] {
      {
        new ApiRegInviteNewUserPojo(new ApiRegNewUserPojo(dynamicEmail(), invite.roleId()).newUser(), invite.expected()),
        load().registration().success().completeRegistration().payload(),
        load().registration().success().completeRegistration().expected()
      }
    };
  }

  @DataProvider(name = "onlyAdminCanInviteUser")
  public static Object[][] onlyAdminCanInviteUser() {
    return load().registration().unsuccessful().onlyAdmin().stream()
      .map(tc -> new Object[] {
        tc.role(), new ApiRegNewUserPojo(dynamicEmail(), tc.roleId()).newUser(), tc.expected()
      })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "invalidFields")
  public static Object[][] invalidFields() {
    return load().registration().unsuccessful().invalidFields().stream()
      .map(tc -> new Object[] {
        tc.description(),
        aggregateNewUserEMail(tc.newUser()),
        tc.expected()
      }).toArray(Object[][]::new);
  }

  @DataProvider(name = "invalidInvite")
  public static Object[][] invalidInvite() {
    return new Object[][] {
      {
        "expired invite token",
        JwtUtil.generateRegistrationInviteToken(
          new NewUserDetails("id", "unknow@example.com", EnvUtil.getEnv("DEVELOPER_ROLE_ID")),
          EnvUtil.getEnv("JWT_SECRET"),
          new Date(System.currentTimeMillis() - 200L),
          new Date(System.currentTimeMillis() - 100L)),
        new ApiRegExpectedPojo(400, "schemas/users/registration/invalidInvite.json")
      },
      {
        "invalid token",
        "dkjfauds9aewaldsjga09w8509wpfojsdgiuq09tusdlkjfadsoitu",
        new ApiRegExpectedPojo(400, "schemas/users/registration/invalidInvite.json")
      }
    };
  }
}
