package com.talentradar.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthRequestSpecs {

  public static RequestSpecification defaultSpec() {
    return new RequestSpecBuilder()
      .addRequestSpecification(RequestSpecs.defaultSpec())
      .setBasePath("/auth")
      .build();
  }

  public static RequestSpecification withAuthSpec(String token) {
    return new RequestSpecBuilder()
      .addRequestSpecification(RequestSpecs.withAuthSpec(token))
      .setBasePath("/auth")
      .build();
  }
}
