package com.witcher.ttrpgapi.service;

import com.witcher.ttrpgapi.character.Character;
import com.witcher.ttrpgapi.repository.CharacterJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private CharacterJdbcRepository characterJdbcRepo;

    @Autowired
    public void CharacterJdbcRepository (CharacterJdbcRepository characterJdbcRepository) {
        this.characterJdbcRepo = characterJdbcRepository;
    }

    public int currentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        long id = jwt.getClaim("id");
        return (int) id;
    }


    //**********************************
    //          ONLY FOR DEBUG
    //**********************************

    public Character getCharacterByUserId(int id){
        return characterJdbcRepo.getCharacterById(id);
    }

    //**********************************
    //          ONLY FOR DEBUG
    //**********************************




    public List<Character> getAllCharactersByUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        long id = jwt.getClaim("id");
        int userId = (int) id;

        return characterJdbcRepo.getAllCharactersByUserId(userId);
    }

    public Character getCharacterById(Integer characterId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        long id = jwt.getClaim("id");
        int userId = (int) id;

        return characterJdbcRepo.getCharacterByIdAndUserId(characterId,userId);
    }


    public boolean createNewCharacter(Character character){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        long id = jwt.getClaim("id");
        int userId = (int) id;


     // have key attributes?
        if(character.getName() != null && character.getProfession() != null && character.getRace() != null){
            // already exist with that name?
            if(characterJdbcRepo.getCharacterByUserIdAndName(userId,character.getName()) == null){
                characterJdbcRepo.createNewCharacter(character, userId);
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public boolean updateCharacterAttribute(String attribute, String value, int characterId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        long id = jwt.getClaim("id");
        int userId = (int) id;

        AttributeValidator validator = new AttributeValidator();

        // permission check and validate
        if(characterJdbcRepo.getUserHasCharacter(characterId, userId) && validator.attributeValidation(attribute)){

           characterJdbcRepo.updateCharacterAttributeById(characterId, attribute, value);
            return true;
        }

        return false;
    }

    public boolean deleteCharacter(int characterId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        long id = jwt.getClaim("id");
        int userId = (int) id;

        if(characterJdbcRepo.getUserHasCharacter(characterId, userId)){
        characterJdbcRepo.deleteCharacterById(characterId);
        return true;
        }
        return false;
    }

}
