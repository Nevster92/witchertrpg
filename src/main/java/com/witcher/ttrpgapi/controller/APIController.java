package com.witcher.ttrpgapi.controller;

import com.witcher.ttrpgapi.character.Character;
import com.witcher.ttrpgapi.security.config.TokenService;
import com.witcher.ttrpgapi.service.AttributeValidator;
import com.witcher.ttrpgapi.service.CharacterService;

import model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class APIController {

    @Autowired
    TokenService tokenService;


    private CharacterService characterService;

    @Autowired
    public void CharacterService (CharacterService characterService) {
        this.characterService = characterService;
    }



    @CrossOrigin
    @RequestMapping("/")
    public String index(Principal principal){
        return "Főoldal "+ principal.getName();
    }


    @CrossOrigin
    @RequestMapping("/stories")
    public String stories(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return "username: "+currentPrincipalName;
    }

    @CrossOrigin
    @RequestMapping("/noauth")
    public String fail(){


        return "SIKEEEEER";
    }

    @CrossOrigin
    @RequestMapping("/testall")
    public Character testAll() {

//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Jwt jwt = (Jwt) authentication.getPrincipal();
//        long userId =  jwt.getClaim("id");
//
//        String currentPrincipalName = authentication.getName();
        return characterService.getCharacterByUserId(3);
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


