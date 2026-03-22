package com.example.Brainstorm.Controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;

import com.example.Brainstorm.Config.JwtUtil;
import com.example.Brainstorm.DTO.Urequest;
import com.example.Brainstorm.Models.User;
import com.example.Brainstorm.Services.QuizService;
import com.example.Brainstorm.Services.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(Usercontroller.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private QuizService quizService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    void testHomePageWithLoggedInUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setUsername("john123");

        when(quizService.getAllQuiz()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/Brainstorm/home")
                .sessionAttr("loggedInUser", user))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("quizzes"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user));

        verify(quizService).getAllQuiz();
    }

    @Test
    void testHomePageWithoutLoggedInUser() throws Exception {
        when(quizService.getAllQuiz()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/Brainstorm/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("quizzes"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", (Object) null));

        verify(quizService).getAllQuiz();
    }

    @Test
    void testAdminDashboard() throws Exception {
        mockMvc.perform(get("/Brainstorm/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("Admindashboard"));
    }

    @Test
    void testUserList() throws Exception {
        User user1 = new User();
        user1.setUsername("john");

        User user2 = new User();
        user2.setUsername("alice");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/Brainstorm/userlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("Userlist"))
                .andExpect(model().attributeExists("users"));

        verify(userService).getAllUsers();
    }

    @Test
    void testUserEditWhenUserExists() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("john123");
        user.setName("John");

        when(userService.getUserbyUsername("john123")).thenReturn(user);

        mockMvc.perform(get("/Brainstorm/useredit/john123"))
                .andExpect(status().isOk())
                .andExpect(view().name("Usereditform"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user));

        verify(userService).getUserbyUsername("john123");
    }

    @Test
    void testUserEditWhenUserDoesNotExist() throws Exception {
        when(userService.getUserbyUsername("unknown")).thenReturn(null);

        mockMvc.perform(get("/Brainstorm/useredit/unknown"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Brainstorm/home"));

        verify(userService).getUserbyUsername("unknown");
    }

    @Test
    void testUserUpdate() throws Exception {
        mockMvc.perform(post("/Brainstorm/useredit/john123")
                .param("name", "John Updated")
                .param("age", "25")
                .param("email", "johnupdated@example.com")
                .param("number", "9999999999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Brainstorm/home"));

        verify(userService).Updateuser(eq("john123"), any(Urequest.class));
    }

    @Test
    void testUserDeletion() throws Exception {
        doNothing().when(userService).Deleteuser(1L);

        mockMvc.perform(get("/Brainstorm/deleteuser/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Brainstorm/home"));

        verify(userService).Deleteuser(1L);
    }
}