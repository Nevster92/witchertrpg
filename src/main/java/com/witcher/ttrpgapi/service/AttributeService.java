package com.witcher.ttrpgapi.service;

import org.springframework.stereotype.Service;

@Service
public class AttributeService {


// TODO EZ lehet nemis kell


    public boolean updateAttribute(int characterId, String attrKey, int attrValue){
        switch(attrKey) {
            case "hp":
              return hpUpdate(characterId, attrValue);
            case "ref":
                // code block
                break;
            default:
           return false;
        }
        return false;

    }


    public boolean hpUpdate(int characterId, int attrValue){
    return false;
    }



}
