package com.witcher.ttrpgapi.service;

import com.witcher.ttrpgapi.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class WsService {

    private CampaignRepository campaignRepository;
    @Autowired
    public void CampaignRepository(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }
    public boolean wsRoomAcces(String url, long userId){
        int lastIndex = url.lastIndexOf('/');
        if (lastIndex != -1) {
            String result = url.substring(lastIndex + 1);
            int campaignId = Integer.parseInt(result);
            int userIdConverted = (int) userId;

            if(campaignRepository.userIsPlayer(campaignId,userIdConverted) != 0 || campaignRepository.hasCampaign(campaignId, userIdConverted)){
                return true;
            }
            return false;

        } else {
            return false;
        }
    }

}
