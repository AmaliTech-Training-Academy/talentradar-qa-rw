package com.talentradar.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtil {

  private static JwtBuilder buildJwt(Map<String, Object> claims, String secret, Date issuedAt, Date expiredAt) {
    JwtBuilder jwtBuilder = Jwts.builder()
      .setIssuedAt(issuedAt)
      .setExpiration(expiredAt)
      .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)), SignatureAlgorithm.HS256);
    claims.forEach(jwtBuilder::claim);
    return jwtBuilder;
  }

  public static String generateToken(Map<String, Object> claims, String secret, Date issuedAt, Date expiredAt) {
    String email = (String) claims.get("email");
    return buildJwt(claims, secret, issuedAt, expiredAt)
      .setSubject(email)
      .compact();
  }

  public static String generateRegistrationInviteToken(Map<String, Object> claims, String secret, Date issuedAt, Date expiredAt) {
    return buildJwt(claims, secret, issuedAt, expiredAt)
      .compact();
  }

  public static String generateValidToken(String role) {
    switch (role) {
      case "admin" -> {
        return generateToken(
          Map.of("email", EnvUtil.getEnv("ADMIN_EMAIL"), "userId", EnvUtil.getEnv("ADMIN_ID")),
          EnvUtil.getEnv("JWT_SECRET"), new Date(),
          new Date(System.currentTimeMillis() + 15 * 60 * 1000));
      }
      case "manager" -> {
        return generateToken(
          Map.of("email", EnvUtil.getEnv("MANAGER_EMAIL"), "userId", EnvUtil.getEnv("MANAGER_ID")),
          EnvUtil.getEnv("JWT_SECRET"), new Date(),
          new Date(System.currentTimeMillis() + 15 * 60 * 1000));
      }
      default -> {
        return generateToken(
          Map.of("email", EnvUtil.getEnv("DEVELOPER_EMAIL"), "userId", EnvUtil.getEnv("DEVELOPER_ID")),
          EnvUtil.getEnv("JWT_SECRET"), new Date(),
          new Date(System.currentTimeMillis() + 15 * 60 * 1000));
      }
    }
  }
}
