package com.example.Brainstorm.Controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Brainstorm.DTO.Qrequest;
import com.example.Brainstorm.Models.Quiz;
import com.example.Brainstorm.Services.QuizService;
import com.example.Brainstorm.Services.UserService;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/quiz")
public class Quizcontroller {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;

    @GetMapping("/quizzes")
    public String AllQuizzes(Model model) {
        List<Quiz> quiz = quizService.getAllQuiz();
        model.addAttribute("quiz", quiz);
        return "Quizlist";
    }

    @GetMapping("/createform")
    @PreAuthorize("hasRole('ADMIN')")
    public String quizform(@ModelAttribute("qrequest") Qrequest qrequest ) {
        return "Quizcreation";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createform(@ModelAttribute("qrequest") Qrequest qrequest) {
    quizService.createQuiz(qrequest);
    return "redirect:/quiz/quizzes";
    }

    @GetMapping("/update/{quizId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showUpdateQuizForm(@PathVariable Long quizId, Model model) {
    Quiz quiz = quizService.getQuizById(quizId);
    model.addAttribute("quiz", quiz);
    return "Quizeditform";
    }

    @PostMapping("/update/{quizId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateQuiz(@PathVariable Long quizId,@ModelAttribute Qrequest qrequest) {
    quizService.updateQuiz(quizId, qrequest);
    return "redirect:/quiz/quizzes";
    }

    @GetMapping("/delete/{quizId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteQuiz(@PathVariable Long quizId) {
    quizService.deleteQuiz(quizId);
    return "redirect:/quiz/quizzes";
    }
    
}
