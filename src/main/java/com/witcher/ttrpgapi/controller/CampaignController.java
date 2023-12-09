package com.witcher.ttrpgapi.controller;

import com.witcher.ttrpgapi.pojo.Campaign;
import com.witcher.ttrpgapi.pojo.Character;
import com.witcher.ttrpgapi.pojo.request.AbillityRollReq;
import com.witcher.ttrpgapi.pojo.request.JoinReq;
import com.witcher.ttrpgapi.service.ActionService;
import com.witcher.ttrpgapi.service.CampaignService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CampaignController {
    private final ResponseEntity OK = ResponseEntity.status(HttpStatus.OK.value()).build();
    private final ResponseEntity ERROR = ResponseEntity.badRequest().build();
    private final ResponseEntity TEST = ResponseEntity.ok("Teszt!!!!");

    private CampaignService campaignService;
    private ActionService actionService;

    @Autowired
    public void CampaignService(CampaignService campaignService){
        this.campaignService = campaignService;
    }

    @Autowired
    public void ActionService(ActionService actionService){
        this.actionService = actionService;
    }

    @CrossOrigin
    @PostMapping("/campaign/new")
    ResponseEntity<?> createNewCampaign(@RequestBody @Valid Campaign campaign){
        if( campaignService.createNewCampaign(campaign)){
            return OK;
        }
        return ERROR;
    }

    @CrossOrigin
    @DeleteMapping("/campaign/delete/{id}")
    ResponseEntity<?> deleteCampaign(@PathVariable(value="id") Integer id){

        if( campaignService.deleteCampaign(id)){
            return OK;
        }

        return ERROR;
    }

    @CrossOrigin
    @PutMapping("/campaign/{id}/create_inv")
    ResponseEntity<?> createInvitationLink(@PathVariable(value="id") Integer id){
        if( campaignService.createInvitation(id)){
            return OK;
        }
        return ERROR;
    }

    @CrossOrigin
    @GetMapping("/campaign/{id}/link")
    ResponseEntity<?> getInvitationLink(@PathVariable(value="id") Integer id){
        return campaignService.getInvitation(id);
    }

    // Not working unknown reason 404
//    @CrossOrigin
//    @GetMapping("/campaign/get/all")
//    List<Campaign> getAllCampaign(){
//        return campaignService.getAllCampaign();
//    }



    @CrossOrigin
    @GetMapping("/campaign/get/{id}")
    Campaign getCampaignById(@PathVariable(value="id") Integer id){
        Campaign c = campaignService.getCampaignById(id);
        return c;
    }

    @CrossOrigin
    @GetMapping("/campaign/get/all_as_dm")
    ResponseEntity<?> getAllCampaignAsDm(){
        return ResponseEntity.ok(campaignService.getAllCampaignAsDm());
    }
    @CrossOrigin
    @GetMapping("/campaign/get/all_as_player")
    ResponseEntity<?> getAllCampaignAsPlayer(){
        return ResponseEntity.ok(campaignService.getAllCampaignAsPlayer());
    }



    @CrossOrigin
    @PostMapping("/campaign/join")
    ResponseEntity<?> joinCampaign(@RequestBody @Valid JoinReq joinReq){
        System.out.println(joinReq.getInvitationLink());
        System.out.println(joinReq.getCharacterId());
        return campaignService.joinCampaign(joinReq);
    }

    @CrossOrigin
    @GetMapping("/campaign/roll/abillity")
    ResponseEntity<?> getAbillityRoll(@RequestBody @Valid AbillityRollReq abillityRollReq){
        return actionService.rollAbillity(abillityRollReq);
    }




}
