package com.example.Brainstorm.Controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Brainstorm.DTO.Arequest;
import com.example.Brainstorm.DTO.Srequest;
import com.example.Brainstorm.Models.Attempt;
import com.example.Brainstorm.Models.Quiz;
import com.example.Brainstorm.Repositories.QuizRepo;
import com.example.Brainstorm.Services.AttemptService;

@Controller
@RequestMapping("/attempts")
public class AttemptController {

    @Autowired
    private AttemptService attemptService;

    @Autowired
    private QuizRepo quizRepo;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAllAttempts(Model model) {
        model.addAttribute("attempts", attemptService.getAllAttempts());
        return "Attemptlist";
    }

    @GetMapping("/{attemptId}")
    public String showAttempt(@PathVariable Long attemptId, Model model) {
        model.addAttribute("attempt", attemptService.getAttemptById(attemptId));
        return "Attemptview";
    }

    @GetMapping("/take/{quizId}")
    public String takeQuiz(@PathVariable Long quizId, Model model) {
    Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));

    Srequest request = new Srequest();
    request.setAnswers(new ArrayList<>());

    quiz.getQuestions().forEach(q -> {
        Arequest a = new Arequest();
        a.setQuestionId(q.getId());
        request.getAnswers().add(a);
    });

    model.addAttribute("quiz", quiz);
    model.addAttribute("submission", request);

    return "QuizAttempt";
    }

    @PostMapping("/submit/{quizId}")
    public String submitQuiz(@PathVariable Long quizId, @ModelAttribute Srequest request, Model model) {
        Attempt attempt = attemptService.submitQuiz(quizId, request);
        model.addAttribute("attempt", attempt);
        return "Quizresult";
    }
    
}
