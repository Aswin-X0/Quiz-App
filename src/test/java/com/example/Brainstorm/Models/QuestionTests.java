package com.example.Brainstorm.Models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuestionTests {

    @Test
    void testAllArgsConstructor() {
        Quiz quiz = new Quiz(); 
        quiz.setId(1L);

        Question question = new Question(
                1L,
                "What is Java?",
                "Language",
                "Animal",
                "Car",
                "Food",
                "Language",
                quiz
        );

        assertEquals(1L, question.getId());
        assertEquals("What is Java?", question.getQuestionTitle());
        assertEquals("Language", question.getOption1());
        assertEquals("Animal", question.getOption2());
        assertEquals("Car", question.getOption3());
        assertEquals("Food", question.getOption4());
        assertEquals("Language", question.getCorrectOption());
        assertEquals(quiz, question.getQuiz());
    }

    @Test
    void testSettersAndGetters() {
        Question question = new Question();
        Quiz quiz = new Quiz();
        quiz.setId(2L);

        question.setId(2L);
        question.setQuestionTitle("2 + 2 = ?");
        question.setOption1("3");
        question.setOption2("4");
        question.setOption3("5");
        question.setOption4("6");
        question.setCorrectOption("4");
        question.setQuiz(quiz);

        assertEquals(2L, question.getId());
        assertEquals("2 + 2 = ?", question.getQuestionTitle());
        assertEquals("3", question.getOption1());
        assertEquals("4", question.getOption2());
        assertEquals("5", question.getOption3());
        assertEquals("6", question.getOption4());
        assertEquals("4", question.getCorrectOption());
        assertEquals(quiz, question.getQuiz());
    }

    @Test
    void testCorrectOptionLogic() {
        Question question = new Question();
        question.setCorrectOption("option2");

        assertEquals("option2", question.getCorrectOption());
    }

    @Test
    void testEqualsAndHashCode() {
        Quiz quiz = new Quiz();
        quiz.setId(1L);

        Question q1 = new Question(1L, "Q", "A", "B", "C", "D", "A", quiz);
        Question q2 = new Question(1L, "Q", "A", "B", "C", "D", "A", quiz);

        assertEquals(q1, q2);
        assertEquals(q1.hashCode(), q2.hashCode());
    }
}