package com.thoughtworks.grad.configuration.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

@Component
public class JWTIssuer {

    @Value("${security.jwt.secret:_SEMS_JWT_SECRET_201708240805987}")
    private String jwtSecret;

    @Value("${security.jwt.expiration-in-seconds}")
    private long expirationInSeconds;

    public String generateToken(Map<String, Object> payload) {
        return Jwts.builder()
                .setClaims(payload)
                .setExpiration(new Date(System.currentTimeMillis() + expirationInSeconds * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String extractAuthorizedPayload(String jwtToken) {
        try {
            return new ObjectMapper().writeValueAsString(Jwts.parser().setSigningKey(jwtSecret)
                    .parseClaimsJws(jwtToken)
                    .getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Parse object to json error.", e);
        }
    }
}
