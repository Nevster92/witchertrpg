package com.witcher.ttrpgapi.controller;

import com.witcher.ttrpgapi.security.config.TokenService;
import com.witcher.ttrpgapi.service.UserService;
import com.witcher.ttrpgapi.user.User;
import com.witcher.ttrpgapi.user.UserDTO;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private UserService userService;
    private final TokenService tokenService;
    public AuthController (TokenService tokenService){
        this.tokenService = tokenService;
    }
    @Autowired
    public void UserService (UserService userService) {
        this.userService = userService;
    }
    @CrossOrigin
    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody @Valid User userDetails){
        if(userService.createNewUser(userDetails)){
            return ResponseEntity.status(HttpStatus.OK.value()).build();
        }else{
            return ResponseEntity.badRequest().build();
        }

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
    @GetMapping("/token/validate")
    public ResponseEntity<?> testPost() {
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @GetMapping("/user/details")
    public UserDTO getUserData() {

        return userService.getUserData();
    }



    @CrossOrigin
    @PostMapping("/login")
    String postLogin() {
        return "login";
    }

    @CrossOrigin
    @PostMapping("/user/update")
    public ResponseEntity<?>  editUser(@RequestBody @Valid User user ) {
        userService.modifyUser(user);
        return ResponseEntity.ok().build();
    }


}
