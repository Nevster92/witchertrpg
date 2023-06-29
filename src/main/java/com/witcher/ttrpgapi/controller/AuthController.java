package com.witcher.ttrpgapi.controller;

import com.witcher.ttrpgapi.security.config.TokenService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);


    private final TokenService tokenService;
    public AuthController (TokenService tokenService){
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public String token(Authentication authentication){
        LOG.debug("Tokent kért a user: '{')", authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Tokent kapott: '{')", token);
        return token;
    }
}
