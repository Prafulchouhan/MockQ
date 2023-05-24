package com.server.mock.Service.impl;

import com.server.mock.Service.QuestionService;
import com.server.mock.model.exam.Question;
import com.server.mock.model.exam.Quiz;
import com.server.mock.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@CacheConfig(cacheNames = {"question","questions"})
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Override
    @CachePut(key = "#id",value = "question")
    public Question createQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    @CachePut(key = "#id",value = "question")
    public Question updateQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    @Cacheable(value = "question")
    public Set<Question> getAllQuestion() {
        return new LinkedHashSet<>(this.questionRepository.findAll());
    }

    @Override
    @Cacheable(key = "#id",value = "question")
    public Question getQuestionById(Long id) {
        return this.questionRepository.findById(id).get();
    }

    @Override
    @CacheEvict(key = "#id",value = "question")
    public void deleteQuestion(Long id) {
        this.questionRepository.deleteById(id);
    }

    @Override
    @Cacheable(key = "#id",value = "question")
    public Set<Question> findByQuid(Quiz quiz) {
        return this.questionRepository.findByQuiz(quiz);
    }
}
