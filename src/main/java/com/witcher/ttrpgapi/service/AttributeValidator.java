package com.witcher.ttrpgapi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Getter

public class AttributeValidator {

    private List<String> attributes;

    public AttributeValidator (){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Olvasd be a JSON fájlt egy List<String>-be
            List<String> stringList = objectMapper.readValue(new File("src/main/resources/attributes.json"), new TypeReference<List<String>>() {});
            setAttributes(stringList);
            // Most már rendelkezel a beolvasott String listával

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void setAttributes(List<String> attributes){
        this.attributes = attributes;
    }

    public boolean attributeValidation ( String attr){
        if(this.attributes.contains(attr)){
            return true;
        }
        return false;
    }

}
