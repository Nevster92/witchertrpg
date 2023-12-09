package com.witcher.ttrpgapi.repository;

import com.witcher.ttrpgapi.pojo.Campaign;
import com.witcher.ttrpgapi.pojo.Character;
import com.witcher.ttrpgapi.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CampaignRepository {

    private JdbcTemplate jdbc;

    @Autowired
    public CampaignRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    public boolean hasCampaign( int campaignId, int userId){
        String sql = "select 1 from campaign where id = ? AND user_id = ?";

        try {
            jdbc.queryForObject(sql, Integer.class, campaignId, userId);
            return true;
        }catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    public void createCampaign(Campaign campaignDetails){
        String sql = "INSERT INTO campaign ( user_id, title, description, invitation_link) VALUES (?, ?, ?,?)";
          try{
              jdbc.update(sql, campaignDetails.getUserId(), campaignDetails.getTitle(), campaignDetails.getDescription(), campaignDetails.getInvitationLink());
          }catch (Error e){
              throw e;
          }
    }
    public void deleteCampaign(int campaignId){
            // DELETE FOREIGN KEYS
            String sql = "update user_characters SET campaign_id = null WHERE campaign_id = ?";
            jdbc.update(sql, campaignId);

            //TODO elöbb a külső kulcsokat is törölni kell
            sql = "DELETE FROM campaign WHERE id = ?;";
            jdbc.update(sql, campaignId);
    }


    public void updateInvitationLink(int campaignId, String generatedString) {
        try {
            String sql = "UPDATE campaign SET invitation_link = ? WHERE id = ?";
            jdbc.update(sql, generatedString, campaignId);
        }catch (Error e){
            throw e;
        }

    }

    public String getInvitationLink(Integer campaignId) {
        String sql = "SELECT invitation_link FROM campaign WHERE id = ?";

        return jdbc.queryForObject(sql, String.class, campaignId);
    }

    public int findCampaignIdByInvitationLink(String invitationLink){
        String sql = "SELECT id FROM campaign WHERE invitation_link = ?";
    try {
        return  jdbc.queryForObject(sql, Integer.class, invitationLink);

    }catch (Exception e){
        throw e;
    }

    }

    public List<Campaign> getAllCampaignAsDmByUserId(int userId) {
        String sql = "SELECT * FROM campaign  WHERE user_id = ?";

        return jdbc.query(
                sql,
                new Object[]{userId},
                new BeanPropertyRowMapper<>(Campaign.class));
    }
    public List<Campaign> getAllCampaignAsPlayerByUserId(int userId) {
        String sql = "SELECT c.* FROM campaign c JOIN user_characters uc ON c.id = uc.campaign_id WHERE uc.user_id = ?";

        return jdbc.query(
                sql,
                new Object[]{userId},
                new BeanPropertyRowMapper<>(Campaign.class));
    }
    public void putCharacterToCampaign(int characterId, int campaignId) {

        String sql = "update user_characters SET campaign_id = ? WHERE character_id = ?";
        try {
            jdbc.update(sql,campaignId,characterId);
        }catch (Error e){
            throw e;
        }

    }


    public int userIsPlayer(int campaignId, int userId) {
        String sql = "select character_id from user_characters where campaign_id = ? AND user_id = ?";

        try {
           return jdbc.queryForObject(sql, Integer.class, campaignId, userId);

        }catch (EmptyResultDataAccessException e){
            return 0;
        }
    }

    public Campaign getCampaignById(int campaignId) {
        String sql = "SELECT * FROM campaign  WHERE id = ?";

        return (Campaign) jdbc.queryForObject(
                sql,
                new Object[]{campaignId},
                new BeanPropertyRowMapper(Campaign.class));
    }





}
