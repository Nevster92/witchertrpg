package com.witcher.ttrpgapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class APIController {


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


}


