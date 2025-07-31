package com.talentradar.dto.seeds;

public record UserSeedDto(String id, String email, String username, String name,
                          String password, String role, String status) {}
