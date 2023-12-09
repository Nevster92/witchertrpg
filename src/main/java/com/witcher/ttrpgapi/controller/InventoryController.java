package com.witcher.ttrpgapi.controller;

import com.witcher.ttrpgapi.pojo.Armor;
import com.witcher.ttrpgapi.pojo.Weapon;
import com.witcher.ttrpgapi.pojo.request.UpdateItem;
import com.witcher.ttrpgapi.pojo.request.WearigItem;
import com.witcher.ttrpgapi.service.CharacterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class InventoryController {
    private final ResponseEntity OK = ResponseEntity.status(HttpStatus.OK.value()).build();
    private final ResponseEntity ERROR = ResponseEntity.badRequest().build();
    private final ResponseEntity TEST = ResponseEntity.ok("Teszt!!!!");


    private CharacterService characterService;

    @Autowired
    public void CharacterService(CharacterService characterService){
        this.characterService = characterService;
    }

    @CrossOrigin
    @GetMapping("/all_armor")
    List<Armor> getArmorsFromRepo(){

        return characterService.getAllArmorFromRepo();
    }

    @CrossOrigin
    @GetMapping("/all_weapon")
    List<Weapon> getWeaponsFromRepo(){
        return characterService.getAllWeaponFromRepo();
    }

    @CrossOrigin
    @GetMapping("/character/{id}/get/all_armor")
    List<Armor> getArmors(@PathVariable(value="id") Integer characterId){
        return characterService.getAllArmorByCharacterId(characterId);
    }

    @CrossOrigin
    @GetMapping("/character/{id}/get/all_weapon")
    List<Weapon> getWeapons(@PathVariable(value="id") Integer characterId){

        return characterService.getAllWeaponByCharacterId(characterId);
    }
    @CrossOrigin
    @GetMapping("/inventory/weapon/get/{id}")
    Weapon getWeapon(@PathVariable(value="id") Integer weaponId){
        return characterService.getWeapon(weaponId);
    }


    @CrossOrigin
    @GetMapping("/inventory/armor/get/{id}")
    Armor getArmor(@PathVariable(value="id") Integer armorId){
        return characterService.getArmor(armorId);
    }


    @CrossOrigin
    @DeleteMapping("/inventory/weapon/delete/{id}")
    ResponseEntity<?> deleteWeapon(@PathVariable(value="id") Integer weaponId){
        if(characterService.deleteWeaponFromInventory(weaponId)){
            return OK;
        }
        return ERROR;
    }

    @CrossOrigin
    @DeleteMapping("/inventory/armor/delete/{id}")
    ResponseEntity<?> deleteArmor(@PathVariable(value="id") Integer armorID){
        if(characterService.deleteArmorFromInventory(armorID)){
            return OK;
        }
        return ERROR;
    }

    @CrossOrigin
    @PostMapping("/character/{id}/inventory/weapon/add")
    ResponseEntity<?>  addNewWeaponToInventory(@RequestBody @Valid Weapon weapon, @PathVariable(value="id") Integer characterId){
        try {
            if(characterService.addNewWeapon(characterId, weapon)){
                return OK;
            }
            return ERROR;
        }catch (Error e){
            return ERROR;
        }
    }


    @CrossOrigin
    @PostMapping("/character/{id}/inventory/armor/add")
    ResponseEntity<?>  addNewArmorToInventory(@RequestBody @Valid Armor armor,@PathVariable(value="id") Integer characterId){
        try {
            if(characterService.addNewArmor(characterId, armor)){
                return OK;
            }
            return ERROR;
        }catch (Error e){

            return ERROR;
        }
    }

    @CrossOrigin
    @PostMapping("/inventory/mod/weapon")
    ResponseEntity<?>modifyWeaponAttribute(@RequestBody @Valid UpdateItem updateDetails){

        if(characterService.updateWeaponAttribute(updateDetails)){
            return OK;
        }

        return ERROR;
    }
    @CrossOrigin
    @PostMapping("/inventory/mod/armor")
    ResponseEntity<?>modifyArmorAttribute(@RequestBody @Valid UpdateItem updateDetails){
        if(characterService.updateArmorAttribute(updateDetails)){
            return OK;
        }
        return ERROR;
    }


    @CrossOrigin
    @PutMapping("/character/inventory/wear")
    ResponseEntity<?>wearItemToCharacter(@RequestBody @Valid WearigItem details){
            return characterService.wearItem(details);
    }

    @CrossOrigin
    @DeleteMapping("/character/{id}/dropitem/{bodypart}")
    ResponseEntity<?>dropItem(@PathVariable(value="bodypart") String bodypart, @PathVariable (value = "id") Integer characterId){
       return characterService.dropItem(characterId, bodypart);

    }

    @CrossOrigin
    @GetMapping("/character/{id}/wear_weapon")
    List<Weapon> getWearedWeapon(@PathVariable(value="id") Integer characterId){
        return characterService.getAllWearedWeaponByCharacterId(characterId);
    }


    @CrossOrigin
    @GetMapping("/character/{id}/wear_armor")
    List<Armor> getWearedtArmor(@PathVariable(value="id") Integer characterId){
        return characterService.getAllWearedArmorByCharacterId(characterId);
    }



}
