package ru.artemkliucharov.luodingoserver.luodingo_server.service;


import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.AppUser;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    /**
     * Extracting username from token
     *
     * @param token token
     * @return username
     */

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Token generation
     *
     * @param userDetails user's data
     * @return token
     */
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        if(userDetails instanceof AppUser customAppUserDetails){
            claims.put("id", customAppUserDetails.getId());
            claims.put("email", customAppUserDetails.getEmail());
        }
        return generateToken(claims, userDetails);
    }

    /**
     * Validate token
     *
     * @param token token
     * @param userDetails user's data
     * @return if valid -> true
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    /**
     * Extracting data from token
     *
     * @param token token
     * @param claimsResolver func for extract data
     * @param <T> data type
     * @return data
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    /**
     * Token generation
     *
     * @param extraClaims additional data
     * @param userDetails
     * @return
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Checking is token expired
     *
     * @param token token
     * @return if not expired -> false
     */
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracting expiration date
     *
     * @param token token
     * @return expiration date
     */
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }


    /**
     * Extracting all data from token
     *
     * @param token token
     * @return data
     */
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * Receiving key for sing token
     *
     * @return key
     */
    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
