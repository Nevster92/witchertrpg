package com.witcher.ttrpgapi.service;

import com.witcher.ttrpgapi.pojo.Character;
import com.witcher.ttrpgapi.pojo.request.AbillityRollReq;
import com.witcher.ttrpgapi.repository.CampaignRepository;
import com.witcher.ttrpgapi.repository.CharacterJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service

public class ActionService {

    private static final String NO_PERMISSION = "You have no permission for the character!" ;
    private static final String INVALID_ITEM_ID = "Invalid item id!" ;
    private static final String DATABASE_ERROR = "Database Error!";

    private CampaignRepository campaignRepository;
    private CharacterJdbcRepository characterRepository;
    private AttributeValidator attributeValidator = new AttributeValidator();
    @Autowired
    public void CharacterJdbcRepository(CharacterJdbcRepository characterRepository) {
        this.characterRepository = characterRepository;
    }
    @Autowired
    public void CampaignRepository(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public int currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        long id = jwt.getClaim("id");
        return (int) id;
    }


    public  ResponseEntity<?> rollAbillity(AbillityRollReq abillityRollReq) {
        if(!characterRepository.getUserHasCharacter(abillityRollReq.getCharacterId(), currentUserId()) ){
            return ResponseEntity.badRequest().body(NO_PERMISSION);
        }
        if(!attributeValidator.characterAttributeValidation(abillityRollReq.getAbillityString())){
            return ResponseEntity.badRequest().body(DATABASE_ERROR);
        }

        Character character = characterRepository.getCharacterById(abillityRollReq.getCharacterId());
        return ResponseEntity.ok(character.rollAbillity(abillityRollReq.getAbillityString()));
    }
}
