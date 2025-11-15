package org.example.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
  @Value("${jwt.secret}")
  private String jwtSecret;

  private SecretKey key;

  private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

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
      logger.warn("Invalid JWT signature: {}", e.getMessage());
    } catch (io.jsonwebtoken.MalformedJwtException e) {
      logger.warn("Invalid JWT token: {}", e.getMessage());
    } catch (io.jsonwebtoken.ExpiredJwtException e) {
      logger.debug("JWT token is expired: {}", e.getMessage());
    } catch (io.jsonwebtoken.UnsupportedJwtException e) {
      logger.warn("JWT token is unsupported: {}", e.getMessage());
    } catch (java.lang.IllegalArgumentException e) {
      logger.warn("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }
}