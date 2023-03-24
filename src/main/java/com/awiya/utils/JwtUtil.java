package com.awiya.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final String SECRET_KEY = "secret_key";
    private static final int VALIDITY = 3600 * 5;

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String userNameFromToken = getUserNameFromToken(token);
        return (userNameFromToken.equals(userDetails.getUsername()) && !isTokenExpired((token)));
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims allClaimsFromToken = getAllClaimsFromToken(token);
        return claimResolver.apply(allClaimsFromToken);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }


}
