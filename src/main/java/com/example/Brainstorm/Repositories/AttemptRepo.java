package com.example.Brainstorm.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Brainstorm.Models.Attempt;

@Repository
public interface AttemptRepo extends JpaRepository <Attempt , Long> {
       List<Attempt> findByUsername(String username);

} 