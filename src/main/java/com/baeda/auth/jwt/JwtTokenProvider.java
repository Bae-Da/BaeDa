package com.baeda.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import org.springframework.util.StringUtils;

@Slf4j
@Getter
@Component
public class JwtTokenProvider {

    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private String generateToken(Map<String, Object> claims,
        String subject,
        int expirationMinutes,
        String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMinutes * 1000L * 60);

        JwtBuilder builder = Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(key);

        if (claims != null) {
            builder.setClaims(claims);
        }
        if (subject != null) {
            builder.setSubject(subject);
        }

        return builder.compact();
    }

    public String generateAccessToken(Map<String, Object> claims, String subject,
        String base64EncodedSecretKey) {
        return generateToken(claims, subject, accessTokenExpirationMinutes, base64EncodedSecretKey);
    }

    public String generateRefreshToken(String subject, String base64EncodedSecretKey) {
        return generateToken(null, subject, refreshTokenExpirationMinutes, base64EncodedSecretKey);
    }

    public Date getTokenExpiration(int expirationMinutes) {
        return new Date(System.currentTimeMillis() + expirationMinutes * 1000L * 60);
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Jws<Claims> parseToken(String token, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token, encodeBase64SecretKey(secretKey));
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    public String getSubjectFromRefreshToken(String refreshToken) {
        try {
            Jws<Claims> claims = parseToken(refreshToken, encodeBase64SecretKey(secretKey));
            return claims.getBody().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid refresh token: {}", e.getMessage());
            return null;
        }
    }

    public Claims getClaims(String token) {
        try {
            return parseToken(token, encodeBase64SecretKey(secretKey)).getBody();
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Failed to get claims from token: {}", e.getMessage());
            return null;
        }
    }

    public void addTokenToCookie(HttpServletResponse response, String name, String token, int maxAge) {
        Cookie cookie = new Cookie(name, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public String getTokenFromCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return getTokenFromCookie(request, "access_token");
    }

    public void setTokenCookie(HttpServletResponse response, String token) {
        addTokenToCookie(response, "access_token", token, accessTokenExpirationMinutes);
    }

    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        addTokenToCookie(response, "refresh_token", refreshToken, refreshTokenExpirationMinutes);
    }
}
