package com.server.mock.controller;

import com.server.mock.Service.QuestionService;
import com.server.mock.Service.UserService;
import com.server.mock.Service.impl.UserServiceImpl;
import com.server.mock.model.User;
import com.server.mock.model.exam.QuizAttempt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/attempts/{userId}")
    public ResponseEntity<List<QuizAttempt>> getQuizAttemptsById(@PathVariable("userId") Long userId){
        List<QuizAttempt> quizAttempts = this.userService.findQuizAttemptsByUserId(userId);
        return new ResponseEntity<>(quizAttempts, HttpStatus.OK);
    }

    @PostMapping("/attempts/{userId}")
    public ResponseEntity<String> addAttemptOfQuizToUser(@PathVariable("userId") Long userId,
                                                         @RequestBody QuizAttempt quizAttempt){
        User user = this.userService.findById(userId);
        user.getQuizAttempts().add(quizAttempt);
        this.userService.updateUser(user);

        return new ResponseEntity<>("successfully added Quiz Attempt",HttpStatus.OK);
    }

    @GetMapping("/current_user")
    public User getUser(@Valid Principal principal){
        return this.userService.getName(principal.getName()).get();
    }


    @GetMapping
    public List<User> getUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.findById(id);
    }
}
