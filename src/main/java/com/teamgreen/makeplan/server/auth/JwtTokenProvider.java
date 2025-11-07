package com.teamgreen.makeplan.server.auth;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final String SECRET_KEY = "ThisIsASecretKeyThatMustBeAtLeast32CharactersLong!";
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Access: 30minutes, Refresh: 2weeks
    private static final long ACCESS_TOKEN_VALIDITY = 1000L * 60 * 30;


    public String buildJwtToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY);

        return Jwts.builder()
                   .setSubject(email)
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(key, SignatureAlgorithm.HS256)
                   .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

}
