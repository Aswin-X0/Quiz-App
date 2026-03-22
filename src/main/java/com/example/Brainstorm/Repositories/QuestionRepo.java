package com.example.Brainstorm.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Brainstorm.Models.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question , Long> {
    List<Question> findByQuizId(Long quizId);
    List<Question> findByQuizTitle(String title);

    List<Question>findByQuizCategory(String category);    
} 