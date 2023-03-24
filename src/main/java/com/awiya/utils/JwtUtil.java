package com.awiya.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final String SECRET_KEY = "secret_key";

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

}
