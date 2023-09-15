package com.witcher.ttrpgapi.controller;

import com.witcher.ttrpgapi.security.config.TokenService;
import com.witcher.ttrpgapi.service.UserService;
import com.witcher.ttrpgapi.user.User;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private UserService userService;
    private final TokenService tokenService;
    public AuthController (TokenService tokenService){
        this.tokenService = tokenService;
    }



    @Autowired
    public void UserService (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/token")
    public ResponseEntity<?> token(Authentication authentication){
        try {
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @CrossOrigin
    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody User userDetails){
      if(userService.createNewUser(userDetails)){
          return ResponseEntity.status(HttpStatus.OK.value()).build();
      }else{
          return ResponseEntity.badRequest().build();
      }

    }
}
