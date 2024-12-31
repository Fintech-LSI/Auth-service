package com.fintech.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secretKey;
  @Value("${jwt.expiration_time}")
  private long EXPIRATION_TIME; // 10 hours

  public long getEXPIRATION_TIME() {
    return EXPIRATION_TIME;
  }
  public String getSecretKey() {
    return secretKey;
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String email) {
    return Jwts.builder()
      .setSubject(email)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .signWith(getSigningKey())  // SignatureAlgorithm is now automatically selected
      .compact();
  }

  public String extractEmail(String token) {
    Claims claims = Jwts.parser()  // Use parserBuilder() instead of parser()
      .setSigningKey(getSigningKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
    return claims.getSubject();
  }
}
