package com.witcher.ttrpgapi.controller;

import com.witcher.ttrpgapi.security.config.TokenService;
import model.TestModel;
import model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class APIController {

    @Autowired
    TokenService tokenService;

    @CrossOrigin
    @RequestMapping("/")
    public String index(Principal principal){
        return "Főoldal "+ principal.getName();
    }


    @CrossOrigin
    @RequestMapping("/stories")
    public String stories(){
        return "Stories";
    }


    @CrossOrigin
    @RequestMapping("/fail")
    public String fail(){
        return "FAIL";
    }

    @CrossOrigin
    @RequestMapping("/delete")
    public String delete()
    {
        System.out.println("NA A DELETE LEGALÁB LEFUT");
        return "Delete";
    }

    @CrossOrigin
    @PostMapping("/login")
    String postLogin() {
        return "login";
    }


    @CrossOrigin
    @PostMapping("/token/validate")
    public Token testPost(@RequestBody Token token) {
        Token ret = tokenService.TokenValidate(token);
        return ret;
    }


}


