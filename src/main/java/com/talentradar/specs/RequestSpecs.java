package com.talentradar.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Cookie;
import io.restassured.specification.RequestSpecification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestSpecs {

  public static RequestSpecification defaultSpec() {
    return new RequestSpecBuilder()
      .setContentType("application/json")
      .log(LogDetail.ALL)
      .build();
  }

  public static RequestSpecification withAuthSpec(String token) {
    // Define cookie expiration
    Date expiryDate = new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(3600));
    // Sign cookie
    Cookie signedCookie = new Cookie.Builder("token", token)
      .setPath("/")
      .setSecured(true)
      .setHttpOnly(true)
      .setExpiryDate(expiryDate)
      .build();

    return new RequestSpecBuilder()
      .addRequestSpecification(defaultSpec())
      .addHeader("Authorization", "Bearer " + token)
      .addCookie(signedCookie)
      .build();
  }
}
