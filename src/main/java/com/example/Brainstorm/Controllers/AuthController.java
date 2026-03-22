package com.example.Brainstorm.Controllers;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Brainstorm.DTO.Urequest;
import com.example.Brainstorm.Models.User;
import com.example.Brainstorm.Services.Authservice;
import com.example.Brainstorm.Services.UserService;


@Controller
@RequestMapping("/Brainstorm")
public class AuthController {

   @Autowired
   private Authservice authService;

   @Autowired
    private UserService userService;


   @ModelAttribute("urequest")
    public Urequest urequest() {
        return new Urequest();
    }

   @GetMapping("/signup")
    @PreAuthorize("permitAll()")
    public String Signup(){
        return "Register";
    }

   @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public String signUp(@ModelAttribute("urequest") Urequest urequest, Model model) {
    String result = authService.SignUp(urequest);

        if (!result.equals("User registered successfully")) {
            model.addAttribute("error", result);
            return "Register";
        }

        return "redirect:/Brainstorm/login";
    }

   @GetMapping("/login")
    @PreAuthorize("permitAll()")
    public String loginPage() {
        return "Login";
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public String signIn(@RequestParam String username, @RequestParam String password,HttpSession session, Model model) {
        try {
            String jwt = authService.SignIn(username, password);
            User user = userService.getUserbyUsername(username);
            session.setAttribute("loggedInUser", user);
            session.setAttribute("jwt", jwt);
            return "redirect:/Brainstorm/home";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid username or password");
            return "Login";
        }
}
}