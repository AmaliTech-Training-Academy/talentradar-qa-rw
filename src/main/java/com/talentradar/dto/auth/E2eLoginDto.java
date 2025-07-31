package com.talentradar.dto.auth;

import java.util.List;

public record E2eLoginDto(List<E2eLoginSceneDto> success,
                          List<E2eLoginSceneDto> failure,
                          List<E2eLoginSceneDto> validation,
                          List<E2eLoginSceneDto> xss) {}
