package com.example.Brainstorm.Controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import com.example.Brainstorm.Config.JwtUtil;
import com.example.Brainstorm.DTO.Orequest;
import com.example.Brainstorm.Models.Question;
import com.example.Brainstorm.Services.Questionservice;
import com.example.Brainstorm.Services.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;


@WebMvcTest(QuestionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Questionservice questionservice;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    void testQuestionList() throws Exception {
        when(questionservice.allquestions()).thenReturn(Arrays.asList(new Question(), new Question()));

        mockMvc.perform(get("/Questions/questionlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("Questionlist"))
                .andExpect(model().attributeExists("questions"));

        verify(questionservice, times(1)).allquestions();
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/Questions/create/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("Questioncreate"))
                .andExpect(model().attributeExists("quizId"))
                .andExpect(model().attributeExists("questionRequest"));
    }

    @Test
    void testCreateQuestion() throws Exception {
        doNothing().when(questionservice).createquestion(eq(1L), any(Orequest.class));

        mockMvc.perform(post("/Questions/create/1")
                .param("questionTitle", "Test Question")
                .param("option1", "A")
                .param("option2", "B")
                .param("option3", "C")
                .param("option4", "D")
                .param("correctOption", "A"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Questions/questionlist"));

        verify(questionservice).createquestion(eq(1L), any(Orequest.class));
    }

    @Test
    void testShowUpdateForm() throws Exception {
        mockMvc.perform(get("/Questions/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("Questionedit"))
                .andExpect(model().attributeExists("questionId"))
                .andExpect(model().attributeExists("questionRequest"));
    }

    @Test
    void testUpdateQuestion() throws Exception {
        doNothing().when(questionservice).updatequestion(eq(1L), any(Orequest.class));

        mockMvc.perform(post("/Questions/edit/1")
                .param("questionTitle", "Updated Question")
                .param("option1", "A")
                .param("option2", "B")
                .param("option3", "C")
                .param("option4", "D")
                .param("correctOption", "A"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Questions/questionlist"));

        verify(questionservice).updatequestion(eq(1L), any(Orequest.class));
    }

    @Test
    void testDeleteQuestion() throws Exception {
        doNothing().when(questionservice).deletequestion(1L);

        mockMvc.perform(get("/Questions/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Questions/questionlist"));

        verify(questionservice).deletequestion(1L);
    }
}