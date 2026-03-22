package com.example.Brainstorm.Services;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Brainstorm.DTO.Qrequest;
import com.example.Brainstorm.Models.Quiz;
import com.example.Brainstorm.Repositories.QuizRepo;

@Service
public class QuizService {

    @Autowired
    private QuizRepo quizRepo;

    public List<Quiz> getAllQuiz(){
        return quizRepo.findAll();   
    }

    public List<Quiz> getQuizByTitle(String title){
        return quizRepo.findByTitle(title);
    }
    public Quiz getQuizById(Long quizId) {
        return quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));
    }

    public List<Quiz> getQuizByCategory(String category){
        return quizRepo.findByCategory(category);
    }

    public Quiz createQuiz(Qrequest qrequest) {
    
    Quiz quiz = new Quiz();
    quiz.setTitle(qrequest.getTitle());
    quiz.setDescription(qrequest.getDescription());
    quiz.setCategory(qrequest.getCategory());
    quiz.setCreatedAt(LocalDateTime.now());
    quiz.setUpdatedAt(LocalDateTime.now());

    return quizRepo.save(quiz);
    }

    public Quiz updateQuiz(Long quizId, Qrequest qrequest) {
    Quiz existingQuiz = quizRepo.findById(quizId).orElse(null);

    if (existingQuiz != null) {
        existingQuiz.setTitle(qrequest.getTitle());
        existingQuiz.setDescription(qrequest.getDescription());
        existingQuiz.setCategory(qrequest.getCategory());
        existingQuiz.setUpdatedAt(LocalDateTime.now());

        return quizRepo.save(existingQuiz);
    }
    return null;
    }

    public void deleteQuiz(Long quizId) {
    Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));
    quizRepo.delete(quiz);
    }
}
