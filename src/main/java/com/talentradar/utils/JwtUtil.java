package com.talentradar.utils;

import com.talentradar.pojo.LoginCredentials;
import com.talentradar.pojo.NewUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.Key;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtil {

  private static Key key(String jwtSecret) {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public static String generateToken(LoginCredentials claims, String secret, Date issuedAt, Date expiredAt) {
    return Jwts.builder()
      .setSubject(claims.id())
      .claim("email", claims.email())
      .claim("fullName", claims.name())
      .claim("role", claims.role())
      .setIssuedAt(issuedAt)
      .setExpiration(expiredAt)
      .signWith(key(secret))
      .compact();
  }

  public static String generateRegistrationInviteToken(NewUserDetails claims, String secret, Date issuedAt, Date expiredAt) {
    return Jwts.builder()
      .setSubject(claims.id())
      .claim("email", claims.email())
      .claim("roleId", claims.roleId())
      .setIssuedAt(issuedAt)
      .setExpiration(expiredAt)
      .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
      .compact();
  }

  public static String generateValidToken(String role) {
    switch (role) {
      case "admin" -> {
        return generateToken(
          new LoginCredentials(EnvUtil.getEnv("ADMIN_ID"),
                               EnvUtil.getEnv("ADMIN_EMAIL"), "Admin User", "ADMIN"),
          EnvUtil.getEnv("JWT_SECRET"), new Date(),
          new Date(System.currentTimeMillis() + 15 * 60 * 1000));
      }
      case "manager" -> {
        return generateToken(
          new LoginCredentials(EnvUtil.getEnv("MANAGER_ID"),
                               EnvUtil.getEnv("MANAGER_EMAIL"), "Manager User", "MANAGER"),
          EnvUtil.getEnv("JWT_SECRET"), new Date(),
          new Date(System.currentTimeMillis() + 15 * 60 * 1000));
      }
      default -> {
        return generateToken(
          new LoginCredentials(EnvUtil.getEnv("DEVELOPER_ID"),
                               EnvUtil.getEnv("DEVELOPER_EMAIL"), "Developer User", "DEVELOPER"),
          EnvUtil.getEnv("JWT_SECRET"), new Date(),
          new Date(System.currentTimeMillis() + 15 * 60 * 1000));
      }
    }
  }
}
