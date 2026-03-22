package com.example.Brainstorm.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Brainstorm.DTO.Orequest;
import com.example.Brainstorm.Services.Questionservice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/Questions")
public class QuestionController {

    @Autowired
    private Questionservice questionservice;

    @GetMapping("/questionlist")
    public String Questionlist(Model model) {
        model.addAttribute("questions" ,questionservice.allquestions() );
        return "Questionlist";
    }

    @GetMapping("/create/{quizId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showcreateform(@PathVariable Long quizId, Model model) {
        model.addAttribute("quizId", quizId);
        model.addAttribute("questionRequest", new Orequest());
        return "Questioncreate";
    }

    @PostMapping("/create/{quizId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String createquestion(@PathVariable Long quizId,
        @ModelAttribute("questionRequest") Orequest orequest) {
        questionservice.createquestion(quizId, orequest);
        return "redirect:/Questions/questionlist";
    }

    @GetMapping("/edit/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showUpdateForm(@PathVariable Long questionId, Model model) {
        model.addAttribute("questionId", questionId);
        model.addAttribute("questionRequest", new Orequest());
        return "Questionedit";
    }

    @PostMapping("/edit/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateQuestion(@PathVariable Long questionId,@ModelAttribute("questionRequest") Orequest request) {
        questionservice.updatequestion(questionId, request);
        return "redirect:/Questions/questionlist";
    }
    @GetMapping("/delete/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteQuestion(@PathVariable Long questionId) {
        questionservice.deletequestion(questionId);
        return "redirect:/Questions/questionlist";
    }

}