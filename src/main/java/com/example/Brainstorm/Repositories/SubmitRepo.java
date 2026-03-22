package com.example.Brainstorm.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Brainstorm.Models.Submit;

@Repository
public interface SubmitRepo extends JpaRepository <Submit , Long> {
     List<Submit> findByQuizAttemptId(Long quizAttemptId);

    
} 