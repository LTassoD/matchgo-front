package com.example.appecologica.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final Key signingKey;
    private final int expirationMinutes;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration-minutes}") int expirationMinutes) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMinutes = expirationMinutes;
    }

    public String createToken(String subject) {
        Instant now = Instant.now();
        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(expirationMinutes, ChronoUnit.MINUTES)))
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact();
    }
}
