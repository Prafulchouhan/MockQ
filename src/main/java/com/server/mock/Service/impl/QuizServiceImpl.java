package com.server.mock.Service.impl;

import com.server.mock.Service.QuizService;
import com.server.mock.model.exam.Quiz;
import com.server.mock.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository QuizRepository;

    @Override
    public Quiz createQuiz(Quiz quiz) {
        return this.QuizRepository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        System.out.println("quiz = " + quiz);
        return this.QuizRepository.save(quiz);
    }

    @Override
    public Set<Quiz> getAllQuiz() {
        Set<Quiz> quiz= this.QuizRepository.findAll()
                .stream()
                .map(e->{
                    e.setNoOfQuestions(String.valueOf(e.getQuestions().size()));
                    return e;
                }).collect(Collectors.toSet());
        return new LinkedHashSet<>(quiz);
    }

    @Override
    public Quiz getQuizById(Long id) {
        return this.QuizRepository.findById(id).get();
    }

    @Override
    public List<Quiz> getQuizByCategoryId(Long id) {
        return this.QuizRepository.findByCategory_Id(id).get();
    }

    @Override
    public void deleteQuiz(Long id) {
        this.QuizRepository.deleteById(id);
    }
}
