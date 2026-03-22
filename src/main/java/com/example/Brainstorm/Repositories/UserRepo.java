package com.example.Brainstorm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Brainstorm.Models.User;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository < User , Long>{
    User findByUsername(String username);
    
} 
