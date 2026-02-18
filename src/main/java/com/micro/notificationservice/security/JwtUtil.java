package com.micro.notificationservice.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserId(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    //remove this function
    public String generateTestToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}

/*
 * 
 * http://localhost:8085/test/token
 * 
 * geberate token and paste in swagger then its authenticated
 * */

/*
In your LMS architecture, the Notification Service determines whether a JWT is authenticated by verifying the token’s digital signature using a shared secret key. When the User Service generates a JWT during login, it signs the token using this secret key. The token contains three parts: header, payload, and signature. The payload includes user information (like the user ID), and the signature ensures that the token has not been tampered with.

When the Notification Service receives a request with a JWT in the `Authorization` header, it does not call the User Service to check if the token is valid. Instead, it validates the token locally. It uses the same secret key to recalculate the signature and compares it with the signature included in the token. If the signatures match and the token has not expired, the token is considered authentic. If the token has been altered, expired, or signed with a different key, the verification fails and the request is rejected with a 401 Unauthorized response.

This approach works because only trusted services (like the User Service) know the secret key used to sign tokens. If an attacker tries to modify the token—for example, by changing the user ID—the signature will no longer match, and the Notification Service will immediately detect it. This makes JWT a self-contained and stateless authentication mechanism: each microservice can independently verify identity without contacting another service.

In summary, the Notification Service knows whether a JWT is authenticated by cryptographically verifying its signature using the shared secret key. If the verification succeeds, the user is treated as authenticated; if it fails, access is denied.*/
