package com.talentradar.dto;

import lombok.Data;

@Data
public class ApiExpectedResponseDto {
  private int status;
  private String role;
  private String schema;
}
