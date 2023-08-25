package com.witcher.ttrpgapi.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder encoder;
    @Autowired
    private  JwtDecoder jw;


    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication authentication){
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


    public Token TokenValidate(Token token) {
        String tokenString = token.getToken();
        Token newToken = new Token();
        newToken.setToken(token.getToken());
        try{
            if (!tokenString.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                LocalDateTime now = LocalDateTime.now();
                // TODO időzónák maitt +2 óra
                LocalDateTime exp =  LocalDateTime.parse(jw.decode(token.getToken()).getClaims().get("exp").toString(), formatter).plusHours(2);
                if(now.isAfter(exp)){
                    newToken.setValid(false);
                }else{
                    System.out.println(now.toString());
                    System.out.println(exp.toString());

                    newToken.setValid(true);
                }
                newToken.setUsername(jw.decode(token.getToken()).getClaims().get("sub").toString());
                return newToken;
            }
        }catch (JwtValidationException e) {
            newToken.setValid(false);
            return newToken;
        }
        newToken.setValid(false);
        return newToken;
    }



}
