package com.server.mock.controller;

import com.server.mock.Service.QuestionService;
import com.server.mock.Service.QuizService;
import com.server.mock.Service.impl.QuestionServiceImpl;
import com.server.mock.model.exam.Question;
import com.server.mock.model.exam.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/question")
@CrossOrigin("*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    @PostMapping
    public ResponseEntity<?> addQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(this.questionService.createQuestion(question), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(this.questionService.updateQuestion(question), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllQuestion() {
        return new ResponseEntity<>(this.questionService.getAllQuestion(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
        return new ResponseEntity<>(this.questionService.getQuestionById(id), HttpStatus.OK);
    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable Long id){
        Quiz quiz=this.quizService.getQuizById(id);
        List list=new ArrayList<>(quiz.getQuestions());
        if(list.size()> Integer.parseInt( quiz.getNoOfQuestions())){
            list =list.subList(0,Integer.parseInt(quiz.getNoOfQuestions())+1);
        }
        Collections.shuffle(list);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/quiz/all/{id}")
    public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable Long id){
        Quiz quiz=this.quizService.getQuizById(id);
        List list=new ArrayList<>(quiz.getQuestions());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable Long id) {
        this.questionService.deleteQuestion(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}