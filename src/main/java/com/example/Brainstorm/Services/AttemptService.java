package com.example.Brainstorm.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Brainstorm.DTO.Arequest;
import com.example.Brainstorm.DTO.Srequest;
import com.example.Brainstorm.Models.Attempt;
import com.example.Brainstorm.Models.Question;
import com.example.Brainstorm.Models.Quiz;
import com.example.Brainstorm.Models.Submit;
import com.example.Brainstorm.Repositories.AttemptRepo;
import com.example.Brainstorm.Repositories.QuestionRepo;
import com.example.Brainstorm.Repositories.QuizRepo;
import com.example.Brainstorm.Repositories.SubmitRepo;

@Service
public class AttemptService {

    @Autowired
    private AttemptRepo attemptRepo;

    @Autowired
    private SubmitRepo submitRepo;

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private QuizRepo quizRepo;

    public List<Attempt> getAllAttempts() {
        return attemptRepo.findAll();
    }

    public Attempt getAttemptById(Long attemptId) {
        return attemptRepo.findById(attemptId).orElseThrow(() -> new RuntimeException("Attempt not found"));
    }

    public Attempt submitQuiz(Long quizId, Srequest request) {
        Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));

        Attempt attempt = new Attempt();
        attempt.setQuiz(quiz);
        attempt.setUsername(request.getUsername());
        attempt.setTotalQuestions(request.getAnswers().size());
        attempt.setScore(0);

        Attempt savedAttempt = attemptRepo.save(attempt);

        int score = 0;
        List<Submit> submissions = new ArrayList<>();

        for (Arequest arequest : request.getAnswers()) {
            Question question = questionRepo.findById(arequest.getQuestionId()).orElseThrow(() -> new RuntimeException("Question not found"));

             boolean correct = question.getCorrectOption().equals(arequest.getSelectedAnswer());
            if (correct) {
                score++; 
                }
            Submit submit = new Submit();
            submit.setQuestion(question);
            submit.setSelectedOption(arequest.getSelectedAnswer());
            submit.setIsCorrect(correct);
            submit.setQuizAttempt(savedAttempt);
            submissions.add(submit);   
            }
        submitRepo.saveAll(submissions);
        savedAttempt.setScore(score);
        return attemptRepo.save(savedAttempt);
}
}
