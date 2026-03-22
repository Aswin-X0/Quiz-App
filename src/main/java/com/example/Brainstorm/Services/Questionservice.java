package com.example.Brainstorm.Services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Brainstorm.Repositories.QuestionRepo;
import com.example.Brainstorm.Repositories.QuizRepo;
import com.example.Brainstorm.DTO.Orequest;
import com.example.Brainstorm.Models.Question;
import com.example.Brainstorm.Models.Quiz;

@Service
public class Questionservice {

    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private QuizRepo quizRepo;

    public List<Question> allquestions(){
       return questionRepo.findAll();
    }

    public Question createquestion(Long quizId , Orequest orequest){
        Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));

        Question question = new Question();
        question.setQuestionTitle(orequest.getQuestionTitle());
        question.setOption1(orequest.getOption1());
        question.setOption2(orequest.getOption2());
        question.setOption3(orequest.getOption3());
        question.setOption4(orequest.getOption4());
        question.setCorrectOption(orequest.getCorrectOption());
        question.setQuiz(quiz);

        return questionRepo.save(question);
    }

    public Question updatequestion(Long id, Orequest orequest ){
        Question existing = questionRepo.findById(id).orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        existing.setQuestionTitle(orequest.getQuestionTitle());
        existing.setOption1(orequest.getOption1());
        existing.setOption2(orequest.getOption2());
        existing.setOption3(orequest.getOption3());
        existing.setOption4(orequest.getOption4());
        existing.setCorrectOption(orequest.getCorrectOption());

        return questionRepo.save(existing);
    }
    public void deletequestion(Long questionId) {
    Question question = questionRepo.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
    questionRepo.delete(question);
    }
}

