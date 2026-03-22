package com.example.Brainstorm.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.Brainstorm.DTO.Orequest;
import com.example.Brainstorm.Models.Question;
import com.example.Brainstorm.Models.Quiz;
import com.example.Brainstorm.Repositories.QuestionRepo;
import com.example.Brainstorm.Repositories.QuizRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

public class QuestionServiceTest {

    @Mock
    private QuestionRepo questionRepo;

    @Mock
    private QuizRepo quizRepo;

    @InjectMocks
    private Questionservice questionservice;

    private Quiz quiz;
    private Question question;
    private Orequest orequest;

    @BeforeEach
    void setUp() {
        quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Java Quiz");

        question = new Question();
        question.setId(1L);
        question.setQuestionTitle("What is Java?");
        question.setOption1("Language");
        question.setOption2("Animal");
        question.setOption3("Car");
        question.setOption4("Food");
        question.setCorrectOption("Language");
        question.setQuiz(quiz);

        orequest = new Orequest();
        orequest.setQuestionTitle("What is Spring Boot?");
        orequest.setOption1("Framework");
        orequest.setOption2("Database");
        orequest.setOption3("Browser");
        orequest.setOption4("Game");
        orequest.setCorrectOption("Framework");
    }

    @Test
    void testAllQuestions() {
        List<Question> questions = Arrays.asList(question, new Question());
        when(questionRepo.findAll()).thenReturn(questions);

        List<Question> result = questionservice.allquestions();

        assertEquals(2, result.size());
        verify(questionRepo, times(1)).findAll();
    }

    @Test
    void testCreateQuestionSuccess() {
        when(quizRepo.findById(1L)).thenReturn(Optional.of(quiz));
        when(questionRepo.save(any(Question.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Question savedQuestion = questionservice.createquestion(1L, orequest);

        assertNotNull(savedQuestion);
        assertEquals("What is Spring Boot?", savedQuestion.getQuestionTitle());
        assertEquals("Framework", savedQuestion.getOption1());
        assertEquals("Database", savedQuestion.getOption2());
        assertEquals("Browser", savedQuestion.getOption3());
        assertEquals("Game", savedQuestion.getOption4());
        assertEquals("Framework", savedQuestion.getCorrectOption());
        assertEquals(quiz, savedQuestion.getQuiz());

        verify(quizRepo).findById(1L);
        verify(questionRepo).save(any(Question.class));
    }

    @Test
    void testCreateQuestionQuizNotFound() {
        when(quizRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            questionservice.createquestion(1L, orequest);
        });

        assertEquals("Quiz not found", exception.getMessage());
        verify(quizRepo).findById(1L);
        verify(questionRepo, never()).save(any(Question.class));
    }

    @Test
    void testUpdateQuestionSuccess() {
        when(questionRepo.findById(1L)).thenReturn(Optional.of(question));
        when(questionRepo.save(any(Question.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Question updatedQuestion = questionservice.updatequestion(1L, orequest);

        assertNotNull(updatedQuestion);
        assertEquals("What is Spring Boot?", updatedQuestion.getQuestionTitle());
        assertEquals("Framework", updatedQuestion.getOption1());
        assertEquals("Database", updatedQuestion.getOption2());
        assertEquals("Browser", updatedQuestion.getOption3());
        assertEquals("Game", updatedQuestion.getOption4());
        assertEquals("Framework", updatedQuestion.getCorrectOption());

        verify(questionRepo).findById(1L);
        verify(questionRepo).save(question);
    }

    @Test
    void testUpdateQuestionNotFound() {
        when(questionRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            questionservice.updatequestion(1L, orequest);
        });

        assertEquals("Question not found with id: 1", exception.getMessage());
        verify(questionRepo).findById(1L);
        verify(questionRepo, never()).save(any(Question.class));
    }

    @Test
    void testDeleteQuestionSuccess() {
        when(questionRepo.findById(1L)).thenReturn(Optional.of(question));
        doNothing().when(questionRepo).delete(question);

        questionservice.deletequestion(1L);

        verify(questionRepo).findById(1L);
        verify(questionRepo).delete(question);
    }

    @Test
    void testDeleteQuestionNotFound() {
        when(questionRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            questionservice.deletequestion(1L);
        });

        assertEquals("Question not found", exception.getMessage());
        verify(questionRepo).findById(1L);
        verify(questionRepo, never()).delete(any(Question.class));
    }
}