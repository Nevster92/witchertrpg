package com.witcher.ttrpgapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.Map;
@NoArgsConstructor
public class TableReader {


    public Integer physicalTableRead(int calculatedKey, String attributeKey){
        try {
            // .json fájl beolvasása
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(new File("src/main/resources/tables.json"), Map.class);
            // "physicalTable" bejárása
            Map<String, Object> physicalTable = (Map<String, Object>) jsonMap.get("physicalTable");


            Map<String, Object> entry = (Map<String, Object>) physicalTable.get(Integer.toString(calculatedKey));

            int attr = (int) entry.get(attributeKey);
            return attr;


        } catch (IOException e) {
            return null;
        }
    }


    public Integer meleeBonusRead(int bodyValue){
        try {
            // .json fájl beolvasása
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(new File("src/main/resources/tables.json"), Map.class);
            // "physicalTable" bejárása
            Map<String, Object> meleeBonus = (Map<String, Object>) jsonMap.get("melee_bonus");

            int attr = (int) meleeBonus.get(Integer.toString(bodyValue));
            return attr;

        } catch (IOException e) {
            return null;
        }
    }


}


