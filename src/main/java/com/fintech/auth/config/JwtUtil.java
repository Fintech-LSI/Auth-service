package com.fintech.auth.config;

import com.fintech.auth.entity.Role;
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
@Getter
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secretKey;
  @Value("${jwt.expiration_time:0}")
  private long EXPIRATION_TIME; // 10 hours

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String email , Role role) {
    return Jwts.builder()
      .setSubject(email)
      .claim("role", role.toString()) // Add the role as a custom claim
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .signWith(getSigningKey())  // SignatureAlgorithm is now automatically selected
      .compact();
  }

  public String extractEmail(String token) {
    Claims claims = getClaimsFromToken(token);
    return claims.getSubject();
  }
  public String extractRole(String token) {
    Claims claims = getClaimsFromToken(token);
    return claims.getSubject();
  }

  private Claims getClaimsFromToken(String token) {
    return Jwts.parser()  // Use parserBuilder() instead of parser()
      .setSigningKey(getSigningKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }
}
