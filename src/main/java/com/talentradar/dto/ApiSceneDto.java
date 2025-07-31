package com.talentradar.dto;

import lombok.Data;

@Data
public class ApiSceneDto {
  private ApiRequestDto request;
  private ApiExpectedResponseDto expected;
}
