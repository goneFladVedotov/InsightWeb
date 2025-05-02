package com.insightweb.auth.adapters.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JwtTokenUtils(
    @Value("\${jwt.secret}")
    private val secret: String,
    @Value("\${jwt.lifetime}")
    private val jwtLifetime: Duration
) {

    fun getExpiredDateTime(): LocalDateTime {
        return LocalDateTime.now().plus(jwtLifetime)
    }

    fun generateToken(userDetails: UserDetails): String {
        val expired = getExpiredDateTime()
        val expiredInstant = expired.atZone(ZoneId.systemDefault()).toInstant()
        val issuedInstant = expired.minus(jwtLifetime).atZone(ZoneId.systemDefault()).toInstant()
        val issuedDate = Date.from(issuedInstant)
        val expiredDate = Date.from(expiredInstant)
        return Jwts.builder()
            .setSubject(userDetails.username)
            .setIssuedAt(issuedDate)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun getUsername(token: String): String {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
            .subject
    }
}
