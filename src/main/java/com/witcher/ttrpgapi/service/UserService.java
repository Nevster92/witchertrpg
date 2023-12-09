package com.witcher.ttrpgapi.service;

import com.witcher.ttrpgapi.repository.UserRepository;
import com.witcher.ttrpgapi.user.User;
import com.witcher.ttrpgapi.user.UserDTO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class UserService implements UserDetailsService {



    // TODO törlés

    private UserRepository userRepo;

    @Autowired
    public void UserRepository(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public int currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        long id = jwt.getClaim("id");
        return (int) id;
    }
    public User findUserByUsername(String username){
        return userRepo.findUserByUsername(username);
    }

    public boolean createNewUser(User userDetails){
        try {
            BCryptPasswordEncoder brypt  = new BCryptPasswordEncoder();
            userDetails.setPassword(brypt.encode(userDetails.getPassword()));
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

    public void modifyUser(User user) {
        user.setId(currentUserId());
        BCryptPasswordEncoder brypt  = new BCryptPasswordEncoder();
        user.setPassword(brypt.encode(user.getPassword()));
        userRepo.updateUser(user);
    }

    public UserDTO getUserData() {
       return userRepo.getUserData(currentUserId());

    }
}
