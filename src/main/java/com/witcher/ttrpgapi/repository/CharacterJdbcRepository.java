package com.witcher.ttrpgapi.repository;

import com.witcher.ttrpgapi.character.Character;
import com.witcher.ttrpgapi.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CharacterJdbcRepository {

    private JdbcTemplate jdbc;

    @Autowired
    public CharacterJdbcRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }


    public void createNewCharacter(Character newCharDetails, int userId){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO CHARACTER (NAME, PROFESSION, RACE) VALUES (?,?,?) RETURNING ID";
   //   int newCharacterId =  jdbc.update(sql, newCharDetails.getName(), newCharDetails.getProfession(), newCharDetails.getRace());

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newCharDetails.getName());
            ps.setString(2, newCharDetails.getProfession());
            ps.setString(3, newCharDetails.getRace());
            return ps;
        }, keyHolder);


        int newCharacterId = keyHolder.getKey().intValue();

        // TODO külön kéne szedni?
        String user_character_sql = "INSERT INTO user_characters (user_id, character_id) VALUES (?,?)";
        jdbc.update(user_character_sql, userId, newCharacterId);

    }

    public void updateCharacterAttributeById(int characterId, String attribute, String value){
        String sql = "UPDATE character SET "+attribute+" = ? WHERE id = ?;";
        jdbc.update(sql,value, characterId);
    }
    public void deleteCharacterById(int characterId){
        String sql = "DELETE FROM character WHERE id = ?;";
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
