package com.talentradar.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiDescribedSceneDto extends ApiSceneDto {
  private String desc;
}
