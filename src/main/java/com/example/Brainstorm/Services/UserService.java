package com.example.Brainstorm.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Brainstorm.DTO.Urequest;
import com.example.Brainstorm.Models.User;
import com.example.Brainstorm.Repositories.UserRepo;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepo userRepo;

    public List<User>getAllUsers(){
    return userRepo.findAll();
    }

    public User getUserbyId(Long id){
    return userRepo.findById(id).orElse(null);
    }

    public User getUserbyUsername(String username){
    return userRepo.findByUsername(username);
    }

    public User Updateuser(String username, Urequest urequest){
    User existingsUser = userRepo.findByUsername(username);
        if(existingsUser != null){
        existingsUser.setName(urequest.getName());
        existingsUser.setAge(urequest.getAge());
        existingsUser.setEmail(urequest.getEmail());
        existingsUser.setNumber(urequest.getNumber());
        return userRepo.save(existingsUser);
        }     
    return null;
    }

    public void Deleteuser(Long id){
        userRepo.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User appUser = userRepo.findByUsername(username);
                if (appUser == null) {
                throw new UsernameNotFoundException("User not found");
    }
    return org.springframework.security.core.userdetails.User.builder()
        .username(appUser.getUsername())
        .password(appUser.getPassword())
        .roles(appUser.getRole() != null ? appUser.getRole() : "USER")
        .build();
    }
    
}