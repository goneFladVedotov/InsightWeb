package com.insight_web.blackbox.adapters.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    public LocalDateTime getExpiredDateTime() {
        return LocalDateTime.now().plus(jwtLifetime);
    }

    public String generateToken(UserDetails userDetails) {
        var expired = getExpiredDateTime();
        Instant expiredInstant = expired.atZone(ZoneId.systemDefault()).toInstant();
        Instant issuedInstant = expired.minus(jwtLifetime).atZone(ZoneId.systemDefault()).toInstant();
        Date issuedDate = Date.from(issuedInstant);
        Date expiredDate = Date.from(expiredInstant);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
