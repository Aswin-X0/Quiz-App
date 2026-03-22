package com.example.Brainstorm.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Brainstorm.Config.JwtUtil;
import com.example.Brainstorm.DTO.Urequest;
import com.example.Brainstorm.Models.User;
import com.example.Brainstorm.Repositories.UserRepo;



@Service
public class Authservice {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailService mailService;

    public String SignUp(Urequest urequest) {
         if (userRepo.findByUsername(urequest.getUsername()) != null) {
        return "Username already exists";
    }
        User user = new User();
        user.setName(urequest.getName());
        user.setAge(urequest.getAge());
        user.setNumber(urequest.getNumber());
        user.setEmail(urequest.getEmail());
        user.setUsername(urequest.getUsername());
        user.setPassword(passwordEncoder.encode(urequest.getPassword()));
        userRepo.save(user);

        mailService.sendMail(user.getEmail(),
                "Welcome to Brainstorm",
                "Your account has been created successfully!"
        );
        return "User registered successfully";
    }


    public String SignIn(String username , String password){ 
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authManager.authenticate(token); 
        return jwtUtil.generateToken(auth.getName()); 
    }

    public Authentication authenticateUser(String username, String password) {
    UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(username, password);
    return authManager.authenticate(token);
}
}
