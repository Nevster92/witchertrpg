package com.witcher.ttrpgapi.repository;

import com.witcher.ttrpgapi.user.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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





}
