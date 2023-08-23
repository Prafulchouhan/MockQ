package com.server.mock.Service.impl;

import com.server.mock.Service.QuestionService;
import com.server.mock.model.exam.Question;
import com.server.mock.model.exam.Quiz;
import com.server.mock.repository.QuestionRepository;
import com.server.mock.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

//@CacheConfig(cacheNames = {"question","allQuestion"})
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizRepository quizRepository;

    @Override
    @CachePut(key = "#question.id",value = "question")
    public Question createQuestion(Question question) {
        updateQuizQuestionsInCaching(quizRepository.findById(question.getQuiz().getId()).get());
        return this.questionRepository.save(question);
    }

    @CachePut(key = "#quiz.id",value = "allQuestion")
    public Set<Question> updateQuizQuestionsInCaching(Quiz quiz) {
        return this.questionRepository.findByQuiz(quiz);
    }


    @Override
    @CachePut(key = "#question.id",value = "question")
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
    @Cacheable(key = "#quiz.id",value = "allQuestion")
    public Set<Question> findByQuid(Quiz quiz) {
        return this.questionRepository.findByQuiz(quiz);
    }
}
