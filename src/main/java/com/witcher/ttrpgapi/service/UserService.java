package com.witcher.ttrpgapi.service;

import com.witcher.ttrpgapi.repository.UserRepository;
import com.witcher.ttrpgapi.user.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class UserService implements UserDetailsService {


    // TODO ide jöhetnek a registerek meg modositások stb...
    // TODO innen hívódik még a UserREpo

    private UserRepository userRepo;

    @Autowired
    public void UserRepository(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    public User findUserByUsername(String username){
        return userRepo.findUserByUsername(username);
    }

    public boolean createNewUser(User userDetails){
        if(!userDetails.isFilled()){
            return false;
        }

        try {
            userRepo.findUserByUsername(userDetails.getUsername());
            return false;
        }catch (EmptyResultDataAccessException e){
            userRepo.createUser(userDetails);
            return true;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUsername(username);
    }
}
