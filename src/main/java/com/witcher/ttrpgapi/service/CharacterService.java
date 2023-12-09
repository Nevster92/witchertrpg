package com.witcher.ttrpgapi.service;

import com.witcher.ttrpgapi.pojo.Armor;
import com.witcher.ttrpgapi.pojo.Character;
import com.witcher.ttrpgapi.pojo.Weapon;
import com.witcher.ttrpgapi.pojo.request.UpdateItem;
import com.witcher.ttrpgapi.pojo.request.WearigItem;
import com.witcher.ttrpgapi.repository.CharacterJdbcRepository;
import com.witcher.ttrpgapi.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {

    private static final String NO_PERMISSION = "You have no permission for the character!" ;
    private static final String INVALID_ITEM_ID = "Invalid item id!" ;
    private static final String DATABASE_ERROR = "Database Error!";

    private CharacterJdbcRepository characterJdbcRepo;
    private InventoryRepository inventoryRepo;


    @Autowired
    public void CharacterJdbcRepository(CharacterJdbcRepository characterJdbcRepository) {
        this.characterJdbcRepo = characterJdbcRepository;
    }

    @Autowired
    public void InventoryRepository(InventoryRepository inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }


    public int currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        long id = jwt.getClaim("id");
        return (int) id;
    }


    //**********************************
    //          ONLY FOR DEBUG
    //**********************************

    public Character getCharacterByUserId(int id) {
        return characterJdbcRepo.getCharacterById(id);
    }

    public String test() {
    if( inventoryRepo.isUserHasAccessToWeapon(1,3)){
        return "VANNNN";
    }
        return "NMICNS";
    }


    //**********************************
    //          ONLY FOR DEBUG
    //**********************************


    public List<Character> getAllCharactersByUserId() {
        return characterJdbcRepo.getAllCharactersByUserId(currentUserId());
    }

    public Character getCharacterById(Integer characterId) {
        return characterJdbcRepo.getCharacterByIdAndUserId(characterId, currentUserId());
    }


    public boolean createNewCharacter(Character character) {
            if (characterJdbcRepo.getCharacterByUserIdAndName(currentUserId(), character.getName()) == null) {
                character.setCalculatedStats();
               characterJdbcRepo.createNewCharacter(character, currentUserId());
                return true;
            } else {
                return false;
            }
    }

    public boolean updateCharacterAttribute(String attribute, Integer value, int characterId) {
        AttributeValidator validator = new AttributeValidator();
        // null check
        if (attribute == null || value == null) {
            return false;
        }

        // permission check and validate
        if (characterJdbcRepo.getUserHasCharacter(characterId, currentUserId()) && validator.characterAttributeValidation(attribute)) {
            characterJdbcRepo.updateCharacterAttributeById(characterId, attribute, value);
            return true;
        }

        return false;
    }

    public boolean deleteCharacter(int characterId) {

        if (characterJdbcRepo.getUserHasCharacter(characterId, currentUserId())) {

            characterJdbcRepo.deleteCharacterById(characterId);
            return true;
        }
        return false;
    }

    public List<Armor> getAllArmorByCharacterId( int characterId) {

        if (characterJdbcRepo.getUserHasCharacter(characterId, currentUserId())) {
            return inventoryRepo.getAllArmorByCharacterId(characterId);
        }
        return new ArrayList<>();

    }


    public List<Weapon> getAllWeaponByCharacterId(Integer characterId) {

        if (characterJdbcRepo.getUserHasCharacter(characterId, currentUserId())) {
            return inventoryRepo.getAllWeaponByCharacterId(characterId);
        }
        return new ArrayList<Weapon>();

    }

    public boolean deleteWeaponFromInventory(int weaponId) {
        int characterId = inventoryRepo.getCharacterIdByWeaponId(weaponId);

        if (characterJdbcRepo.getUserHasCharacter(characterId, currentUserId())) {
            inventoryRepo.deleteWeaponInvenotryByid(weaponId, characterId);
            return true;
        }
        return false;
    }

    public boolean deleteArmorFromInventory(int armorId) {
        int characterId = inventoryRepo.getCharacterIdByArmorId(armorId);

        if (characterJdbcRepo.getUserHasCharacter(characterId, currentUserId())) {
            inventoryRepo.deleteArmorInvenotryByid(armorId);
            return true;
        }
        return false;
    }

    public boolean addNewWeapon(int characterId, Weapon weapon) {

        if (characterJdbcRepo.getUserHasCharacter(characterId, currentUserId())) {
            int newWeaponId = inventoryRepo.putWeaponInventory(characterId, weapon);
            System.out.println(newWeaponId);
            return true;
        }
        return false;

    }

    public boolean addNewArmor(Integer characterId, Armor armor) {
        if (characterJdbcRepo.getUserHasCharacter(characterId, currentUserId())) {
            int newWeaponId = inventoryRepo.putArmorInventory(characterId, armor);
            System.out.println(newWeaponId);
            return true;
        }
        return false;

    }

    public boolean updateWeaponAttribute(UpdateItem updateDetails) {
        AttributeValidator validator = new AttributeValidator();

        if (inventoryRepo.isUserHasAccessToWeapon(currentUserId(), updateDetails.getItemId()) &&
                validator.weaponAttributeValidation(updateDetails.getAttribute())) {
            try {
                inventoryRepo.updateWeaponAttribute(updateDetails);
                return true;
            }catch (Exception e) {
                return false;
            }

        }
        System.out.println(inventoryRepo.isUserHasAccessToWeapon(currentUserId(), updateDetails.getItemId()));
        System.out.println(validator.weaponAttributeValidation(updateDetails.getAttribute()));
        return false;
    }

    public boolean updateArmorAttribute(UpdateItem updateDetails) {
        AttributeValidator validator = new AttributeValidator();

        if (inventoryRepo.isUserHasAccessToArmor(currentUserId(), updateDetails.getItemId()) && validator.armorAttributeValidation(updateDetails.getAttribute())) {
           try {
            inventoryRepo.updateArmorAttribute(updateDetails);
           }catch (Exception e){
               return false;
           }

            return true;
        }
        return false;
    }


    public Weapon getWeapon(Integer weaponId) {
        if (inventoryRepo.isUserHasAccessToWeapon(currentUserId(), weaponId)) {
            System.out.println("getwepon");
           return inventoryRepo.getWeaponById(weaponId);
        }
        return null;
    }
    public Armor getArmor(Integer armorId) {

        if (inventoryRepo.isUserHasAccessToArmor(currentUserId(), armorId)) {
            return inventoryRepo.getArmorById(armorId);
        }
        return null;
    }





    public  ResponseEntity<?>  dropItem(Integer characterId, String bodypart) {
        AttributeValidator validator = new AttributeValidator();

        if(!characterJdbcRepo.getUserHasCharacter(characterId, currentUserId())){
            return ResponseEntity.badRequest().body(NO_PERMISSION);

        }
        if(!validator.weaponSlotValidation(bodypart) && !validator.bodyPartValidation(bodypart) ){
            return ResponseEntity.badRequest().body(INVALID_ITEM_ID);
        }
        inventoryRepo.deleteFromWearing(characterId, bodypart);
        return ResponseEntity.ok("");
    }

    public List<Weapon> getAllWearedWeaponByCharacterId(Integer characterId) {
        if(characterJdbcRepo.getUserHasCharacter(characterId, currentUserId())){
          return inventoryRepo.getAllWearedWeaponByCharacterId(characterId);
        }else{
            return new ArrayList<>();
        }

    }

    public List<Armor> getAllWearedArmorByCharacterId(Integer characterId) {
        if(characterJdbcRepo.getUserHasCharacter(characterId, currentUserId())){
            return inventoryRepo.getAllWearedArmorByCharacterId(characterId);
        }else{
            return new ArrayList<>();
        }


    }

    public List<Armor> getAllArmorFromRepo() {
        return inventoryRepo.getAllArmorFromRepo();
    }

    public List<Weapon> getAllWeaponFromRepo() {
        return inventoryRepo.getAllWeaponFromRepo();
    }

    public ResponseEntity<?> wearItem(WearigItem details) {

        // övé e a kari?
        if(!inventoryRepo.isUserHasAccessToWeapon(currentUserId(), details.getItemId()) ){
            return ResponseEntity.badRequest().body(NO_PERMISSION);
        }

        if(details.getBodyPart().equals("r_arm") || details.getBodyPart().equals("l_arm")){

            // van ilyen weaponja?
            if(inventoryRepo.isCharacterHasWeapon(details.getCharacterId(),details.getItemId())){
                try {
                inventoryRepo.updateCharacterWearing(details.getBodyPart(), details.getItemId(), details.getCharacterId());
                } catch (Error e){
                    return ResponseEntity.badRequest().body(DATABASE_ERROR);
                }
                return ResponseEntity.ok("");
            }
            return ResponseEntity.badRequest().body(INVALID_ITEM_ID);

        }else if( details.getBodyPart().equals("head") ||details.getBodyPart().equals("torso")  || details.getBodyPart().equals("legs")){

            // van ilyen armorja?
            if(inventoryRepo.isCharacterHasArmor(details.getCharacterId(),details.getItemId())){
                try {
                    inventoryRepo.updateCharacterWearing(details.getBodyPart(), details.getItemId(), details.getCharacterId());
                } catch (Error e){
                    return ResponseEntity.badRequest().body(DATABASE_ERROR);
                }
                return ResponseEntity.ok("");
            }
            return ResponseEntity.badRequest().body(INVALID_ITEM_ID);

        }
        return ResponseEntity.badRequest().body("Not a bodypart!");
    }
}