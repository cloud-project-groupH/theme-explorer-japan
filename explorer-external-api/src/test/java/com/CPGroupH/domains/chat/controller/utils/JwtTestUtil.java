package com.CPGroupH.domains.chat.controller.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

public class JwtTestUtil {
    public static String createMockJwt(String secretKey) {
        SecretKey key = hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        return Jwts.builder()
                .setSubject("TestUser")
                .claim("role", "USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
