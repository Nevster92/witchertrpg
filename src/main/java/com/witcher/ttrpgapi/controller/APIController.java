package com.witcher.ttrpgapi.controller;

import com.witcher.ttrpgapi.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class APIController {

/*    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;


    @Autowired
    private AuthenticationManager authenticationManager;

@Autowired
private MyAuthenticationManager myAuthenticationManager;

*/

    @CrossOrigin
    @RequestMapping("/")
    public String index(){
        return "Főoldal";
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

//    @RequestMapping("/login")
//    public String login(){
//        return "LOGINPAGE";
//    }

/*   @PostMapping("/login-process")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        Authentication userpass = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
       System.out.println("EGYÁLTALÁN ELKAPOM?");
       System.out.println(loginRequest.getUsername());
       System.out.println(loginRequest.getPassword());

        try {

            authenticationManager.authenticate(userpass);
            // Sikeres bejelentkezés esetén további műveletek
            return ResponseEntity.ok("Bejelentkezés sikeres");
        } catch (AuthenticationException e) {
            // Sikertelen bejelentkezés esetén hibaüzenet
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Érvénytelen felhasználónév vagy jelszó");
        }
    }*/

    @CrossOrigin
    @GetMapping("/login")
    String login() {
        return "login";
    }

    @CrossOrigin
    @PostMapping("/login")
    String postLogin() {
        return "login";
    }


/*    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@ModelAttribute User loginDto){
        System.out.println("EGYÁLTALÁN ELKAPOM?");

        Authentication authentication = myAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        if(authentication.isAuthenticated()){
            System.out.println("SIKER");
            return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
        }else{
            System.out.println("FAIL");
            return new ResponseEntity<>("SIKERTELEN", HttpStatus.UNAUTHORIZED);
        }

    }*/


}


