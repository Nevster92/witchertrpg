package com.witcher.ttrpgapi.repository;

import com.witcher.ttrpgapi.pojo.Character;
import com.witcher.ttrpgapi.user.User;
import com.witcher.ttrpgapi.user.UserDTO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@NoArgsConstructor
@Repository
public class UserRepository {

    private JdbcTemplate jdbc;

    @Autowired
    public UserRepository(JdbcTemplate jdbc){
    this.jdbc = jdbc;
}

    public User findUserByUsername(String username){
        String sql = "SELECT * FROM user_data WHERE username = ?";

        try {
            return jdbc.queryForObject(sql, new Object[]{username}, (rs, i) -> new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password")
            ));
        } catch (EmptyResultDataAccessException e) {
            throw e;
        }
    }

    public void createUser(User newUser){
        String sql = "INSERT INTO user_data (username, email, password) VALUES (?, ?, ?)";
        jdbc.update(sql, newUser.getUsername(), newUser.getEmail(), newUser.getPassword());
    }

    public void updateUser(User user){
        String sql = "UPDATE user_data SET username = ?, password = ?, email=? WHERE id= ?";
        try {
            jdbc.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getId());
        }catch (Exception e){
            System.out.println(e);
        }
    }


    public UserDTO getUserData(int userId) {
        String sql = "SELECT * FROM user_data WHERE id = ?";

        try {
            return (UserDTO) jdbc.queryForObject(
                    sql,
                    new Object[]{userId},
                    new BeanPropertyRowMapper(UserDTO.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
