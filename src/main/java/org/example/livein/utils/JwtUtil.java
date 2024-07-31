package org.example.livein.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.livein.dto.LoginDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuer("Muhammad's Production")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours expiration
                .signWith(secretKey())
                .compact();
    }

    public String generateRefreshToken(LoginDTO logInDTO) {
        return Jwts.builder()
                .subject(logInDTO.getEmail())
                .issuer("Muhammad's Production")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2)) // 48 hours expiration
                .signWith(secretKey())
                .compact();
    }

    private SecretKey secretKey() {
        byte[] bytes = Decoders.BASE64.decode("9876543219876543219876543219876543219876543219876543219876543210");
        return Keys.hmacShaKeyFor(bytes);
    }

    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUserName(String token) {
        return getClaims(token).getSubject();
    }
}
