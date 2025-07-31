package com.talendradar.providers.api;

import com.talentradar.dto.ApiExpectedResponseDto;
import com.talentradar.dto.ApiRequestDto;
import com.talentradar.dto.NewUserInviteClaimsDto;
import com.talentradar.util.Envs;
import com.talentradar.util.JwtUtil;
import org.testng.annotations.DataProvider;

import java.util.*;

public class RegistrationDataProvider extends APIBaseDataProvider {

  public static String dynamicEmail() {
    try {
      Thread.sleep(2L);
    } catch (InterruptedException ignored) {}
    return "testUser_" + System.currentTimeMillis() + "@example.com";
  }

  public static Map<String, Object> acceptInviteAnonymousRequest() {
    ApiRequestDto request = new ApiRequestDto();
    request.setFullName("Anonymous User");
    request.setPassword(Envs.ANY_USER_PASS);
    request.setConfirmPassword(Envs.ANY_USER_PASS);
    return request.asAcceptInvite();
  }

  @DataProvider(name = "send-invite")
  public static Object[][] sendInvite() {
    return load().registration().succeeded().sendInvite().stream()
      .map(e -> {
        e.getRequest().setEmail(dynamicEmail());
        return new Object[] { e.getRequest(), e.getExpected() };
      })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "accept-invite")
  public static Object[][] acceptInvite() {
    return load().registration().succeeded().acceptInvite().stream()
      .map(e -> {
        e.sendInvite().getRequest().setEmail(dynamicEmail());
        return new Object[] { e.sendInvite(), e.request(), e.expected() };
      })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "already-changed-user")
  public static Object[][] alreadyChangedUser() {
    return load().registration().failed().alreadyChangedUser().stream()
      .map(e -> new Object[] { e.desc(), e.claims(), e.expected() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "only-admin-is-allowed-to-send-invite")
  public static Object[][] onlyAdminIsAllowedToSendInvite() {
    return load().roleBasedProtected().admin().stream()
      .map(e -> {
        e.getRequest().setEmail(dynamicEmail());
        e.getRequest().setRoleId(Envs.DEVELOPER_ROLE_ID);
        return new Object[] { e.getRequest(), e.getExpected() };
      })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "send-invite-validation")
  public static Object[][] sendInviteValidation() {
    return load().registration().failed().validation().sendInvite().stream()
      .map(e -> new Object[] { e.getDesc(), e.getRequest(), e.getExpected() })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "accept-invite-validation")
  public static Object[][] acceptInviteValidation() {
    return load().registration().failed().validation().acceptInvite().stream()
      .map(e -> {
        e.sendInvite().getRequest().setEmail(dynamicEmail());
        return new Object[] { e.desc(), e.sendInvite(), e.request(), e.expected() };
      })
      .toArray(Object[][]::new);
  }

  @DataProvider(name = "expired-invite")
  public static Object[][] expiredInvite() {
    return load().registration().failed().validation().expiredInvite().stream()
      .map(e -> {
        e.sendInvite().getRequest().setEmail(dynamicEmail());
        return new Object[] { e.sendInvite(), e.request(), e.expected() };
      })
      .toArray(Object[][]::new);
  }
}
