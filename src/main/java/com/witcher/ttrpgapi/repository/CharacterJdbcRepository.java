package com.witcher.ttrpgapi.repository;

import com.witcher.ttrpgapi.pojo.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CharacterJdbcRepository {

    private JdbcTemplate jdbc;

    @Autowired
    public CharacterJdbcRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }


    public void createNewCharacter(Character newCharDetails, int userId){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbc);
        String sql = "INSERT INTO CHARACTER (NAME, PROFESSION, RACE, GENDER, INTELLIGENCE, REF, DEX, BODY, WILL, HP, STA, REC, STUN, MAX_HP, MElEE_BONUS, RUN, LEAP, ENC, SPD, CRA, LUCK, EMP) " +
                "VALUES (:name, :profession, :race, :gender, :intelligence, :ref, :dex, :body, :will, :hp, :sta, :rec, :stun, :max_hp, :melee_bonus, :run, :leap, :enc, :spd, :cra, :luck, :emp) " +
                "RETURNING ID";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", newCharDetails.getName());
        paramMap.put("profession", newCharDetails.getProfession());
        paramMap.put("race", newCharDetails.getRace());
        paramMap.put("gender", newCharDetails.getGender());
        paramMap.put("intelligence", newCharDetails.getIntelligence());
        paramMap.put("ref", newCharDetails.getRef());
        paramMap.put("dex", newCharDetails.getDex());
        paramMap.put("body", newCharDetails.getBody());
        paramMap.put("will", newCharDetails.getWill());
        paramMap.put("hp", newCharDetails.getHp());
        paramMap.put("sta", newCharDetails.getSta());
        paramMap.put("rec", newCharDetails.getRec());
        paramMap.put("stun", newCharDetails.getStun());
        paramMap.put("max_hp", newCharDetails.getHp());
        paramMap.put("melee_bonus", newCharDetails.getMeleeBonus());
        paramMap.put("run", newCharDetails.getRun());
        paramMap.put("leap", newCharDetails.getLeap());
        paramMap.put("enc", newCharDetails.getEnc());
        paramMap.put("spd", newCharDetails.getSpd());
        paramMap.put("cra", newCharDetails.getCra());
        paramMap.put("luck", newCharDetails.getLuck());
        paramMap.put("emp", newCharDetails.getEmp());


        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);

        namedParameterJdbcTemplate.update(sql, paramSource, keyHolder);

        //    String sql = "INSERT INTO CHARACTER (NAME, PROFESSION, RACE, GENDER, INTELLIGENCE, REF, DEX, BODY, WILL, HP, STA, REC, STUN, MAX_HP, MElEE_BONUS, RUN, LEAP, ENC, SPD) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING ID";
   //   int newCharacterId =  jdbc.update(sql, newCharDetails.getName(), newCharDetails.getProfession(), newCharDetails.getRace());

//        jdbc.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, newCharDetails.getName());
//            ps.setString(2, newCharDetails.getProfession());
//            ps.setString(3, newCharDetails.getRace());
//            ps.setString(4, newCharDetails.getGender());
//            ps.setInt(5, newCharDetails.getIntelligence());
//            ps.setInt(6, newCharDetails.getRef());
//            ps.setInt(7, newCharDetails.getDex());
//            ps.setInt(8, newCharDetails.getBody());
//            ps.setInt(9, newCharDetails.getWill());
//            ps.setInt(10, newCharDetails.getHp());
//            ps.setInt(11, newCharDetails.getSta());
//            ps.setInt(12, newCharDetails.getRec());
//            ps.setInt(13, newCharDetails.getStun());
//            ps.setInt(14, newCharDetails.getHp());
//            ps.setInt(15, newCharDetails.getMeleeBonus());
//            ps.setInt(16, newCharDetails.getRun());
//            ps.setInt(17, newCharDetails.getLeap());
//            ps.setInt(18, newCharDetails.getEnc());
//            ps.setInt(19, newCharDetails.getSpd());
//
//            return ps;
//        }, keyHolder);


        int newCharacterId = keyHolder.getKey().intValue();

        // TODO külön kéne szedni?
        // attach to user
        String user_character_sql = "INSERT INTO user_characters (user_id, character_id) VALUES (?,?)";
        jdbc.update(user_character_sql, userId, newCharacterId);

        // init bodyparts
        String wearingSql = "INSERT INTO character_wearing (character_id) VALUES (?);";
        jdbc.update(wearingSql, newCharacterId);


    }

    public void updateCharacterAttributeById(int characterId, String attribute, int value){
        String sql = "UPDATE character SET "+attribute+" = ? WHERE id = ?;";
        jdbc.update(sql,value, characterId);
        //TODO nem minden sttring!!!
    }
    public void deleteCharacterById(int characterId){
        // DELETE FOREIGN KEYS
        String sql = "DELETE FROM weapon_inventory WHERE character_id = ?;";
        jdbc.update(sql, characterId);
         sql = "DELETE FROM armor_inventory WHERE character_id = ?;";
        jdbc.update(sql, characterId);
        sql = "DELETE FROM user_characters WHERE character_id = ?;";
        jdbc.update(sql, characterId);
        sql = "DELETE FROM character_wearing WHERE character_id = ?;";
        jdbc.update(sql, characterId);


        //TODO elöbb a külső kulcsokat is törölni kell
         sql = "DELETE FROM character WHERE id = ?;";
        jdbc.update(sql, characterId);
    }

    public Character getCharacterByUserIdAndName(int userId , String name){
        String sql = "SELECT * FROM character JOIN user_characters ON character.id = user_characters.character_id WHERE user_characters.user_id = ? and name = ?";

        try {
            return (Character) jdbc.queryForObject(
                    sql,
                    new Object[]{userId, name},
                    new BeanPropertyRowMapper(Character.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }


    }

    public Character getCharacterById(int id){
        String sql = "SELECT * FROM character WHERE id = ?";

        return (Character) jdbc.queryForObject(
                sql,
                new Object[]{id},
                new BeanPropertyRowMapper(Character.class));
    }
    public Character getCharacterByIdAndUserId(int characterId, int userId){
        String sql = "SELECT * FROM character JOIN user_characters ON character.id = user_characters.character_id WHERE user_characters.user_id = ? and character_id = ?";

        try {
            return (Character) jdbc.queryForObject(
                    sql,
                    new Object[]{userId, characterId},
                    new BeanPropertyRowMapper(Character.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public List<Character> getAllCharactersByUserId(int userId){
        String sql = "SELECT * FROM character JOIN user_characters ON character.id = user_characters.character_id WHERE user_characters.user_id = ?;";

       return jdbc.query(
                sql,
                new Object[]{userId},
                new BeanPropertyRowMapper<>(Character.class));

    }

    public boolean getUserHasCharacter (int characterId, int userId){
        String sql = "select 1 from user_characters where user_id = ? AND character_id = ?";

        try {
            jdbc.queryForObject(sql, Integer.class, userId, characterId);
            return true;
        }catch (EmptyResultDataAccessException e){
            return false;
        }
    }

}
