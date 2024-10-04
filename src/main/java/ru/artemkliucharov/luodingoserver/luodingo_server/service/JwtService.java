package ru.artemkliucharov.luodingoserver.luodingo_server.service;


import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.artemkliucharov.luodingoserver.luodingo_server.entity.AppUser;
import ru.artemkliucharov.luodingoserver.luodingo_server.exeption.UserNotFoundExeption;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;
    private final AppUserService appUserService;

    /**
     * Authorize user by token
     * @param header authorization header with token
     * @return AppUser.class object from db
     * @throws UserNotFoundExeption
     */
    public AppUser authorize(String header){
        if(!header.startsWith("Bearer ") || header == null){
            throw new RuntimeException("Unauthorized");
        }
        var token = header.substring(7);
        try{
            var username = extractUsername(token);
            AppUser appUser = appUserService.getByUsername(username);

            if(appUser != null){
                return  appUser;
            } else {
                throw new UserNotFoundExeption("User with username " + username + " not found");
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Unauthorized");
        }
    }

    /**
     * Extract username from token
     *
     * @param token token
     * @return username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Token generation
     *
     * @param userDetails user data
     * @return token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof AppUser customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("email", customUserDetails.getEmail());
            claims.put("username", customUserDetails.getUsername());
        }
        return generateToken(claims, userDetails);
    }

    /**
     * Token validation
     *
     * @param token token
     * @param userDetails user data
     * @return true if the token is valid
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Extracting data from token
     *
     * @param token token
     * @param claimsResolvers function to extract data
     * @param <T> data type
     * @return data
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Token generation
     *
     * @param extraClaims additional data
     * @param userDetails user data
     * @return token
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Checking if the token is expired
     *
     * @param token token
     * @return true if the token is expired
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracting token expiration date
     *
     * @param token token
     * @return expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracting all data from the token
     *
     * @param token token
     * @return data
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    /**
     * Getting the signing key for the token
     *
     * @return key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

