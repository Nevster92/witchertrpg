package com.witcher.ttrpgapi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AttributeValidator {

   private List<String> weaponFields;
    private List<String> armorFields;
   private List<String> characterFields;
    private List<String> bodyPartField;
    private List<String> weaponSlotField;


    public AttributeValidator (){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
//            List<String> stringList = objectMapper.readValue(new File("src/main/resources/attributes.json"), new TypeReference<List<String>>() {});
//            setAttributes(stringList);
//

            JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/allAttributes.json"));

            this.weaponFields = getFieldList(rootNode, "weapon");
             this.armorFields = getFieldList(rootNode, "armor");
             this.characterFields = getFieldList(rootNode, "character");
             this.bodyPartField = getFieldList(rootNode, "bodyPart");
            this.weaponSlotField = getFieldList(rootNode, "weaponSlot");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void setWeaponFields(List<String> weaponFields){
        this.weaponFields = weaponFields;
    }
    public void setArmorFields(List<String> armorFields){
        this.armorFields = armorFields;
    }
    public void setCharacterFields(List<String> characterFields){
        this.characterFields = characterFields;
    }
    public void setBodyPartField(List<String> bodyPartField){
        this.bodyPartField = bodyPartField;
    }

    public void setWeaponSlotField(List<String> weaponSlotField){
        this.weaponSlotField = weaponSlotField;
    }

    private static List<String> getFieldList(JsonNode rootNode, String category) {
        JsonNode categoryNode = rootNode.get(category);
        List<String> fields = new ArrayList<>();

        if (categoryNode != null) {
            for (JsonNode fieldNode : categoryNode) {
                fields.add(fieldNode.asText());
            }
        }

        return fields;
    }



    public boolean characterAttributeValidation ( String attr){
        if(this.characterFields.contains(attr)){
            return true;
        }
        return false;
    }
    public boolean weaponAttributeValidation ( String attr){
        if(this.weaponFields.contains(attr)){
            return true;
        }
        return false;
    }
    public boolean armorAttributeValidation ( String attr){
        if(this.armorFields.contains(attr)){
            return true;
        }
        return false;
    }
    public boolean bodyPartValidation ( String attr){
        if(this.bodyPartField.contains(attr)){
            return true;
        }
        return false;
    }

    public boolean weaponSlotValidation ( String attr){
        if(this.weaponSlotField.contains(attr)){
            return true;
        }
        return false;
    }


}
