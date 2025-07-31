package com.talentradar.util;

import com.talentradar.dto.AuthClaimsDto;
import com.talentradar.dto.NewUserInviteClaimsDto;
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

  public static String generateToken(AuthClaimsDto claims, String secret, Date issuedAt, Date expiredAt) {
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

  public static String generateRegistrationInvite(NewUserInviteClaimsDto claims, String secret, Date issuedAt, Date expiredAt) {
    return Jwts.builder()
      .setSubject(claims.id())
      .claim("email", claims.email())
      .claim("roleId", claims.roleId())
      .setIssuedAt(issuedAt)
      .setExpiration(expiredAt)
      .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
      .compact();
  }

  public static String generateValidRegInvite(NewUserInviteClaimsDto claims) {
    return generateRegistrationInvite(claims, Envs.JWT_SECRET, new Date(),
                                      new Date(System.currentTimeMillis() + 15 * 60 * 1000));
  }

  public static String generateValidToken(String role) {
    return switch (role) {
      case "admin" -> generateToken(new AuthClaimsDto(Envs.ADMIN_ID, Envs.ADMIN_EMAIL, "Admin User", "ADMIN"),
                                    Envs.JWT_SECRET, new Date(),
                                    new Date(System.currentTimeMillis() + 15 * 60 * 1000));
      case "manager" -> generateToken(new AuthClaimsDto(Envs.MANAGER_ID, Envs.MANAGER_EMAIL, "Manager User", "MANAGER"),
                                      Envs.JWT_SECRET, new Date(),
                                      new Date(System.currentTimeMillis() + 15 * 60 * 1000));
      default -> generateToken(new AuthClaimsDto(Envs.DEVELOPER_ID, Envs.DEVELOPER_EMAIL, "Developer User", "DEVELOPER"),
                               Envs.JWT_SECRET, new Date(),
                               new Date(System.currentTimeMillis() + 15 * 60 * 1000));
    };
  }
}
