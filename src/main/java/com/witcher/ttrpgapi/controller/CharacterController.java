package com.witcher.ttrpgapi.controller;


import com.witcher.ttrpgapi.service.CharacterService;
import com.witcher.ttrpgapi.character.Character;

import model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CharacterController {

    private final ResponseEntity OK = ResponseEntity.status(HttpStatus.OK.value()).build();
    private final ResponseEntity ERROR = ResponseEntity.badRequest().build();


    private CharacterService characterService;

    @Autowired
    public void CharacterService(CharacterService characterService){
        this.characterService = characterService;
    }


    @CrossOrigin
    @PostMapping("/character/new")
    ResponseEntity<?>  createNewCharacter(@RequestBody Character character){
    if( characterService.createNewCharacter(character)){
        return OK;
    }
        return ERROR;
    }

    @CrossOrigin
    @PostMapping("/character/mod")
    ResponseEntity<?>  modifyCharacterAttribute(@RequestBody Map<String, Object> requestBody){
        int characterId = Integer.parseInt((String) requestBody.get("id"));
        String attribute = (String) requestBody.get("attribute");
        String value = (String) requestBody.get("value");

        if(characterService.updateCharacterAttribute(attribute, value, characterId)){
            return OK;
        }
        return ERROR;
    }
    @CrossOrigin
    @PostMapping("/character/delete/{id}")
    ResponseEntity<?>  modifyCharacterAttribute(@PathVariable(value="id") Integer characterId){
        if(characterService.deleteCharacter(characterId)){
            return OK;
        }
        return ERROR;
    }

    @CrossOrigin
    @RequestMapping("/character/all_characters")
    List<Character> getAllCharacters(){
       return characterService.getAllCharactersByUserId();
    }

    @CrossOrigin
    @RequestMapping("/character/get/{id}")
    Character getCharacterByName(@PathVariable(value="id") Integer characterId){
        return characterService.getCharacterById(characterId);
    }





}
