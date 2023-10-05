package com.witcher.ttrpgapi.service;

import com.witcher.ttrpgapi.pojo.Campaign;
import com.witcher.ttrpgapi.pojo.request.JoinReq;
import com.witcher.ttrpgapi.repository.CampaignRepository;
import com.witcher.ttrpgapi.repository.CharacterJdbcRepository;
import com.witcher.ttrpgapi.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CampaignService {

    private CampaignRepository campaignRepository;

    public int currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        long id = jwt.getClaim("id");
        return (int) id;
    }



    @Autowired
    public void CampaignRepository(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }



    public boolean createNewCampaign(Campaign campaign) {
        campaign.setUserId(currentUserId());
        try {
            campaignRepository.createCampaign(campaign);
            return true;
        }catch (Error e){
            return false;
        }
    }

    public boolean deleteCampaign(Integer id) {

        if(!campaignRepository.hasCampaign(id, currentUserId())){
            return false;
        }

        try {
            campaignRepository.deleteCampaign(id);
            return true;
        }catch (Error e){
            return false;
        }
    }

    public boolean createInvitation(Integer campaignId) {

        // user is the dm?
        if(!campaignRepository.hasCampaign(campaignId, currentUserId())){
            return false;
        }

        // GENERATE INVITATION LINK

        int codeLength = 7; // A kód hossza
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder generatedCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < codeLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            generatedCode.append(randomChar);
        }

        String generatedString = generatedCode.toString();
        System.out.println(generatedString);

        try {
            campaignRepository.updateInvitationLink(campaignId,generatedString);
            return true;
        }catch (Error e){
            return false;
        }
    }

    public ResponseEntity<?>  getInvitation(Integer campaignId) {

        // user is the dm?
        if(!campaignRepository.hasCampaign(campaignId, currentUserId())){
            return ResponseEntity.badRequest().body("This is not your Campaign!");
        }

        try {
            String ret = campaignRepository.getInvitationLink(campaignId);
            System.out.println(ret);
           return ResponseEntity.ok(ret);

        }catch (Error e){
            return ResponseEntity.badRequest().body("Something Error!");
        }
    }

    public ResponseEntity<?> joinCampaign(JoinReq joinReq) {

        try {
            int campaignId = campaignRepository.findCampaignIdByInvitationLink(joinReq.getInvitationLink());
                // TODO Find the campaign and give back
            campaignRepository.putCharacterToCampaign(joinReq.getCharacterId(), campaignId);

            return ResponseEntity.ok("Succesfully join to campaign!");
        }catch (Exception e ){
            return ResponseEntity.badRequest().body("Bad Invitation Link");
        }

    }

    public List<Campaign> getAllCampaign() {
        List<Campaign> list = campaignRepository.getAllCampaignByUserId(currentUserId());
        System.out.println(list.get(0).getTitle());
        return list;
    }
}
