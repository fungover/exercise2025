package org.example.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
  @Value("${jwt.secret}")
  private String jwtSecret;

  private SecretKey key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public String generateToken(String email, Long jwtExpirationMs) {
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);
    Date expirationDate = new Date(nowMillis + jwtExpirationMs);

    return Jwts.builder()
            .subject(email)
            .issuedAt(now)
            .expiration(expirationDate)
            .signWith(key)
            .compact();
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
  }

  public boolean validateJwtToken(String token) {
    try {
      Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
      return true;
    } catch (SignatureException e) {
      System.out.println("Invalid JWT signature: " + e.getMessage());
    } catch (io.jsonwebtoken.MalformedJwtException e) {
      System.out.println("Invalid JWT token: " + e.getMessage());
    } catch (io.jsonwebtoken.ExpiredJwtException e) {
      System.out.println("JWT token is expired: " + e.getMessage());
    } catch (io.jsonwebtoken.UnsupportedJwtException e) {
      System.out.println("JWT token is unsupported: " + e.getMessage());
    } catch (java.lang.IllegalArgumentException e) {
      System.out.println("JWT claims string is empty: " + e.getMessage());
    }
    return false;
  }
}