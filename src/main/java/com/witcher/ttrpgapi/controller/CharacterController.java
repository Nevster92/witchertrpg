package com.witcher.ttrpgapi.controller;


import com.witcher.ttrpgapi.pojo.Armor;
import com.witcher.ttrpgapi.pojo.Weapon;
import com.witcher.ttrpgapi.service.CharacterService;
import com.witcher.ttrpgapi.pojo.Character;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class CharacterController {

    private final ResponseEntity OK = ResponseEntity.status(HttpStatus.OK.value()).build();
    private final ResponseEntity ERROR = ResponseEntity.badRequest().build();
    private final ResponseEntity TEST = ResponseEntity.ok("Teszt!!!!");


    private CharacterService characterService;

    @Autowired
    public void CharacterService(CharacterService characterService){
        this.characterService = characterService;
    }


    @CrossOrigin
    @PostMapping("/character/new")
    ResponseEntity<?>  createNewCharacter(@RequestBody @Valid Character character){

    if( characterService.createNewCharacter(character)){
        return OK;
    }

        return ERROR;
    }

    @CrossOrigin
    @PostMapping("/character/mod")
    ResponseEntity<?>  modifyCharacterAttribute(@RequestBody Map<String, Object> requestBody){
        if(requestBody.get("id") == null){
           return ERROR;
        }
        Integer characterId = (Integer) requestBody.get("id");
        String attribute = (String) requestBody.get("attribute");
        Integer value = (Integer) requestBody.get("value");


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
    @GetMapping("/character/all_characters")
    List<Character> getAllCharacters(){
       return characterService.getAllCharactersByUserId();
    }

    @CrossOrigin
    @GetMapping("/character/get/{id}")
    Character getCharacterByName(@PathVariable(value="id") Integer characterId){
        return characterService.getCharacterById(characterId);
    }





}
