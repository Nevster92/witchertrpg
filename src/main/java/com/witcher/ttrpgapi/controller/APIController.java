package com.witcher.ttrpgapi.controller;

import com.witcher.ttrpgapi.pojo.Character;
import com.witcher.ttrpgapi.security.config.TokenService;
import com.witcher.ttrpgapi.service.CharacterService;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

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
    @GetMapping("/stories")
    public String stories(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return "username: "+currentPrincipalName;
    }



    @CrossOrigin
    @GetMapping("/noauth")
    public String fail(){


        return "SIKEEEEER";
    }

    @CrossOrigin
    @GetMapping("/gettest")
    public String getTest() {
        return "";
    }
    @CrossOrigin
    @PostMapping("/posttest/{path}")
    ResponseEntity<?> postTest(@PathVariable(value="path") Integer path,
                              @RequestBody Map<String, Object> requestBody) {
        System.out.println(path);
        System.out.println(requestBody.toString());

        return ResponseEntity.ok("fasza minden tes");
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


