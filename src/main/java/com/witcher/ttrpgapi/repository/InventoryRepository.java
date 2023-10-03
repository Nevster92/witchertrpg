package com.witcher.ttrpgapi.repository;

import com.witcher.ttrpgapi.pojo.Armor;
import com.witcher.ttrpgapi.pojo.Weapon;
import com.witcher.ttrpgapi.pojo.request.UpdateItem;
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
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InventoryRepository {

    private JdbcTemplate jdbc;

    @Autowired
    public InventoryRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    // weapon id alapján melyik karakterhez tartozik a weapon
    // a karakter a bejelentkezett user-hez tartozik e?
    // TODO összevonni?
    public boolean isUserHasAccessToWeapon(int userId, int weaponId){
        try {
      String sql = "SELECT character_id FROM weapon_inventory WHERE id = ?";
      int characterId = jdbc.queryForObject(sql, new Object[]{weaponId}, Integer.class);
        sql = "select 1 from user_characters where user_id = ? AND character_id = ?";

            jdbc.queryForObject(sql, Integer.class, userId, characterId);
            return true;
        }catch (EmptyResultDataAccessException e){

            return false;
        }
    }

    public boolean isUserHasAccessToArmor(int userId, int armorId){
        try {
            String sql = "SELECT character_id FROM armor_inventory WHERE id = ?";
            int characterId = jdbc.queryForObject(sql, new Object[]{armorId}, Integer.class);
            sql = "select 1 from user_characters where user_id = ? AND character_id = ?";

            jdbc.queryForObject(sql, Integer.class, userId, characterId);
            return true;
        }catch (EmptyResultDataAccessException e){
            return false;
        }
    }
    public boolean isCharacterHasArmor(int characterId, int armorId){
        try {
            String sql = "select 1 from armor_inventory where character_id = ? AND id = ?";
            jdbc.queryForObject(sql, Integer.class, characterId, armorId);
            return true;
        }catch (EmptyResultDataAccessException e){
            return false;
        }
    }
    public boolean isCharacterHasWeapon(int characterId, int weaponId){
        try {
            String sql = "select 1 from weapon_inventory where character_id = ? AND id = ?";
            jdbc.queryForObject(sql, Integer.class, characterId, weaponId);
            return true;
        }catch (EmptyResultDataAccessException e){
            return false;
        }
    }



    public List<Armor> getAllArmorByCharacterId(int characterId){
        String sql = "SELECT * FROM armor_inventory WHERE character_id = ?";

        return jdbc.query(
                sql,
                new Object[]{characterId},
                new BeanPropertyRowMapper<>(Armor.class));

    }

    public List<Weapon> getAllWeaponByCharacterId(Integer characterId) {
        String sql = "SELECT * FROM weapon_inventory WHERE character_id = ?";
        return jdbc.query(
                sql,
                new Object[]{characterId},
                new BeanPropertyRowMapper<>(Weapon.class));
    }
    public int getCharacterIdByWeaponId(Integer weaponId) {
        String sql = "SELECT character_id FROM weapon_inventory WHERE id = ?";
        return jdbc.queryForObject(sql, new Object[]{weaponId}, Integer.class);
    }
    public int getCharacterIdByArmorId(int armorId) {
        String sql = "SELECT character_id FROM armor_inventory WHERE id = ?";
        return jdbc.queryForObject(sql, new Object[]{armorId}, Integer.class);
    }

    public void deleteWeaponInvenotryByid(int inventoryId, int characterId){
        String sql = "UPDATE character_wearing SET l_arm = CASE WHEN l_arm = ? THEN NULL ELSE l_arm END, r_arm = CASE WHEN r_arm = ? THEN NULL ELSE r_arm END WHERE character_id = ? AND (l_arm = ? OR r_arm = ?)";
        Map<String, Object> paramMap = new HashMap<>();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbc);

        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        paramMap.put("item", inventoryId);
        paramMap.put("item", inventoryId);
        paramMap.put("item", inventoryId);
        paramMap.put("item", inventoryId);
        paramMap.put("character", characterId);

        jdbc.update(sql, inventoryId, inventoryId, characterId, inventoryId,inventoryId);

        sql = "DELETE FROM weapon_inventory WHERE id = ?;";
        jdbc.update(sql, inventoryId);
    };

    public void deleteArmorInvenotryByid(int armorId) {
        String sql = "DELETE FROM armor_inventory WHERE id = ?;";
        jdbc.update(sql, armorId);
    }

    public int putWeaponInventory(int characterId, Weapon weapon) {
    String sql = "INSERT INTO weapon_inventory (character_id, name, type, weapon_accuracy, availability,damage, reliability, hands_required, range, concealment,enhancements,weight,cost,category, effect) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING ID";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, characterId);
            ps.setString(2, weapon.getName());
            ps.setString(3, weapon.getType());
            ps.setInt(4, weapon.getWeaponAccuracy());
            ps.setString(5, weapon.getAvailability());
            ps.setString(6, weapon.getDamage());
                ps.setInt(7, weapon.getReliability());
                ps.setInt(8, weapon.getHandsRequired());
                ps.setString(9, weapon.getRange());
                ps.setString(10, weapon.getConcealment());
                ps.setInt(11, weapon.getEnhancements());
                ps.setDouble(12, weapon.getWeight());
                ps.setInt(13, weapon.getCost());
                ps.setString(14, weapon.getCategory());
            if (weapon.getEffect() != null) {
                ps.setString(15, weapon.getEffect());
            } else {
                ps.setNull(15, Types.VARCHAR);
            }

            return ps;
        }, keyHolder);
        int newWeaponId = keyHolder.getKey().intValue();
        return newWeaponId;
    }


    public int putArmorInventory(Integer characterId, Armor armor) {
        String sql = "INSERT INTO armor_inventory (character_id, name,category, stopping_power,armor_enhancement,availability,effect,encumbrance_value,weight, price) VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING ID";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, characterId);
            ps.setString(2, armor.getName());
            ps.setString(3, armor.getCategory());
            ps.setInt(4, armor.getStoppingPower());
            ps.setInt(5, armor.getArmorEnhancement());
            ps.setString(6,armor.getAvailability() );
            ps.setString(7,armor.getEffect() );
            ps.setInt(8, armor.getEncumbranceValue());
            ps.setDouble(9,armor.getWeight());
            ps.setInt(10, armor.getPrice());

            return ps;
        }, keyHolder);
        int newWeaponId = keyHolder.getKey().intValue();
        return newWeaponId;
    }

    public void updateWeaponAttribute(UpdateItem updateDetails) {
        String sql = "UPDATE weapon_inventory SET "+updateDetails.getAttribute()+" = ? WHERE id = ?;";
        try {
            if(updateDetails.getAttribute().equals("weapon_accuracy") ||
                    updateDetails.getAttribute().equals("reliability") ||
                    updateDetails.getAttribute().equals("hands_required") ||
                    updateDetails.getAttribute().equals("enhancements") ||
                    updateDetails.getAttribute().equals("cost")){
                jdbc.update(sql, updateDetails.getIntValue(), updateDetails.getItemId());
            }else if(updateDetails.getAttribute().equals("weight")){
                jdbc.update(sql, updateDetails.getDoubleValue(), updateDetails.getItemId());
            }else{
                jdbc.update(sql, updateDetails.getValue(), updateDetails.getItemId());
            }

        } catch (Exception e){

            throw e;
        }



    }

    public void updateArmorAttribute(UpdateItem updateDetails) {
        String sql = "UPDATE armor_inventory SET "+updateDetails.getAttribute()+" = ? WHERE id = ?;";
        try {
            if(updateDetails.getAttribute().equals("stopping_power") ||
                    updateDetails.getAttribute().equals("armor_enhancement") ||
                    updateDetails.getAttribute().equals("encumbrance_value") ||
                    updateDetails.getAttribute().equals("price") ){
                jdbc.update(sql, updateDetails.getIntValue(), updateDetails.getItemId());
            }else if(updateDetails.getAttribute().equals("weight")){
                jdbc.update(sql, updateDetails.getDoubleValue(), updateDetails.getItemId());
            }else{
                jdbc.update(sql, updateDetails.getValue(), updateDetails.getItemId());
            }

        } catch (Exception e){

            throw e;
        }


    }

    public Weapon getWeaponById(Integer weaponId) {
        String sql = "SELECT * FROM weapon_inventory WHERE id = ?";
        try {
            return (Weapon) jdbc.queryForObject(
                    sql,
                    new Object[]{weaponId},
                    new BeanPropertyRowMapper(Weapon.class));
        }catch (EmptyResultDataAccessException e){
            System.out.println("EMPTYYY");
            return null;
        }
    }
    public Armor getArmorById(Integer armorId) {
        String sql = "SELECT * FROM armor_inventory WHERE id = ?";
        try {
            return (Armor) jdbc.queryForObject(
                    sql,
                    new Object[]{armorId},
                    new BeanPropertyRowMapper(Armor.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public void updateCharacterWearing(String bodyPart, int itemId, int characterId){
        String sql = "update character_wearing SET "+bodyPart+" = ? WHERE character_id = ?";
        try {
        jdbc.update(sql,itemId, characterId);
        }catch (Exception e){
            throw e;
        }

    }

    public void deleteFromWearing(Integer characterId, String bodypart) {
        String sql = "UPDATE character_wearing SET "+bodypart+"= NULL WHERE character_id = ?;";
        jdbc.update(sql, characterId);
    }

    public List<Weapon> getAllWearedWeaponByCharacterId(Integer characterId) {
        String sql = "SELECT wi.* FROM character_wearing cw JOIN weapon_inventory wi ON cw.l_arm = wi.id OR cw.r_arm = wi.id WHERE cw.character_id = ?";

        return jdbc.query(
                sql,
                new Object[]{characterId},
                new BeanPropertyRowMapper<>(Weapon.class));

    }

    public List<Armor> getAllWearedArmorByCharacterId(Integer characterId) {
        String sql = "SELECT ai.* FROM character_wearing cw JOIN armor_inventory ai ON cw.head = ai.id OR cw.torso = ai.id OR cw.legs = ai.id WHERE cw.character_id = ?";

        return jdbc.query(
                sql,
                new Object[]{characterId},
                new BeanPropertyRowMapper<>(Armor.class));

    }

    public List<Armor> getAllArmorFromRepo() {
        String sql = "Select * from armor";

        return jdbc.query(
                sql,
                new BeanPropertyRowMapper<>(Armor.class));
    }

    public List<Weapon> getAllWeaponFromRepo() {
        String sql = "Select * from weapon";

        return jdbc.query(
                sql,
                new BeanPropertyRowMapper<>(Weapon.class));

    }
}
