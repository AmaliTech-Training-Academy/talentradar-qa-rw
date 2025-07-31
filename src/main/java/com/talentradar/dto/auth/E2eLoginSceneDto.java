package com.talentradar.dto.auth;

import java.util.Map;

public record E2eLoginSceneDto(String tag, E2eCredentialsDto credentials, String expectedMessage) {}
