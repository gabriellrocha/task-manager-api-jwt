package org.gabriel.todolist.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${secret.key}")
    private String secretKey;

    public String generateToken(UserDetails userDetails) {


        final Date issuedAt =  new Date(System.currentTimeMillis());
        final Date expiredAt = new Date(issuedAt.getTime() + (24 * 60 * 60 * 1000));


        return Jwts.builder()
                .claim("roles", userDetails.getAuthorities())
                .setSubject(userDetails.getUsername()) // sub email
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .setId(UUID.randomUUID().toString()) // jti
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        // todo - JwtParser já realiza validações como: estrutura, assinatura, expiração em UTC - Pesquise

        return Jwts
                .parserBuilder()
                .setAllowedClockSkewSeconds(60) // tolerância exp - segundos
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
