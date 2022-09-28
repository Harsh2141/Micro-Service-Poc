package com.tender247.poc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tender247.poc.config.JwtConfiguration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtProvider {

    @Autowired
    private JwtConfiguration jwtConfiguration;

    /**
     * Generating of token, good page for understading of claims https://datatracker.ietf.org/doc/html/rfc7519#section-4.1
     * @param authentication
     * @return
     */
    public String generateToken(Authentication authentication) {

        Long now = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        try {
			claims.put("userData", new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(authentication.getPrincipal()));
		} catch (JsonProcessingException e) {
			
		}
        
        return Jwts.builder()
                .setSubject(authentication.getName())
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(now + jwtConfiguration.getExpiration() * 1000)) //in milliseconds
                .signWith(Keys.hmacShaKeyFor(jwtConfiguration.getSecret().getBytes()))
                .compact();
    }

    public Claims getClaimsFromJwt(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtConfiguration.getSecret().getBytes()))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public boolean validateJwtToken(String jwtToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtConfiguration.getSecret().getBytes()))
                    .build()
                    .parseClaimsJws(jwtToken);

            return true;
        } catch (SecurityException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
