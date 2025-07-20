package com.talentradar.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static org.hamcrest.Matchers.startsWith;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseSpecs {

  public static ResponseSpecification defaultSpec(int statusCode) {
    return new ResponseSpecBuilder()
      .expectStatusCode(statusCode)
      .expectContentType("application/json")
      .build();
  }

  public static ResponseSpecification authSpec(int statusCode) {
    return new ResponseSpecBuilder()
      .addResponseSpecification(defaultSpec(statusCode))
      .expectHeader("Authorization", startsWith("Bearer "))
      .build();
  }
}
