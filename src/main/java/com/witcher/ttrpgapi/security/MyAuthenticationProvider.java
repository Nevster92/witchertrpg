/*
package com.witcher.ttrpgapi.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    private String uname = "";
    private String upassw = "";
    private String existingUserName = "user";
    private String existingPassword = "kaja";


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("PROVIDER");
        uname = String.valueOf(authentication.getName());
        upassw = String.valueOf(authentication.getCredentials());

        if (uname.equals(existingUserName) && upassw.equals(existingPassword)) {
            UsernamePasswordAuthenticationToken authenticationToken;
            authenticationToken = new UsernamePasswordAuthenticationToken(uname, null, getAuthorities());
            return authenticationToken;
        } else {
            System.out.println("PROVIDER ELSE");

            return null;
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


    private List<GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> roles = Arrays.asList("ADMIN", "USER");
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
    }


}*/
