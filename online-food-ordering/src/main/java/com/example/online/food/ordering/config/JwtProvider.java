package com.example.online.food.ordering.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {
    private SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication){
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles=populatedAuthorities(authorities);
        String jwt = Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 8640000))
                .claim("email", authentication.getName())
                .claim("authorities", roles)
                .signWith(secretKey)
                .compact();
        return jwt;
    }

    public String getEmailFromJwtProvider(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
        String email = (String) claims.get("email");
        return email;
    }

    private String populatedAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auth = new HashSet<>();
        for (GrantedAuthority authority : authorities){
            auth.add(authority.getAuthority());
        }
        return String.join(",",auth);
    }
}
