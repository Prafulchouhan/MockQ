package com.server.mock.controller;

import com.server.mock.Service.QuizService;
import com.server.mock.model.exam.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz")
@CrossOrigin("*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping
    public ResponseEntity<?> addQuiz(@RequestBody Quiz quiz){
        return new ResponseEntity<>(this.quizService.createQuiz(quiz), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateQuiz(@RequestBody Quiz quiz){
        System.out.println("quiz = " + quiz);
        return new ResponseEntity<>(this.quizService.updateQuiz(quiz), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllQuiz(){
        return new ResponseEntity<>(this.quizService.getAllQuiz(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuizById(@PathVariable Long id){
        return new ResponseEntity<>(this.quizService.getQuizById(id),HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getQuizByCategoryId(@PathVariable Long id){
        return new ResponseEntity<>(this.quizService.getQuizByCategoryId(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuizById(@PathVariable Long id){
        this.quizService.deleteQuiz(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
