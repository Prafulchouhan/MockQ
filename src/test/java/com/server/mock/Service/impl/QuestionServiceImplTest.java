package com.server.mock.Service.impl;

import com.server.mock.Service.QuestionService;
import com.server.mock.model.exam.Question;
import com.server.mock.model.exam.Quiz;
import com.server.mock.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class QuestionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Mock
    private QuestionRepository questionRepository;

    private Question question1,question2;
    private Quiz quiz;

    @BeforeEach
    void setUp() {
        quiz=new Quiz(1l,"title","desc","100","20",true,20,null,null);
        question1=new Question(1l,
                quiz,
                "question1",
                "image",
                "opt1",
                "opt1",
                "opt1",
                "opt1",
                "ans");
        question2=new Question(2l,
                quiz,
                "question1",
                "image",
                "opt1",
                "opt1",
                "opt1",
                "opt1",
                "ans");
    }

    @Test
    void createQuestion() {
        Mockito.when(questionRepository.save(question1)).thenReturn(question1);
        assertThat(questionService.createQuestion(question1).getId()).isEqualTo(1l);
    }

    @Test
    void updateQuestion() {
        Mockito.when(questionRepository.save(question1)).thenReturn(question2);
        assertThat(questionService.updateQuestion(question1).getId()).isEqualTo(2l);
    }

    @Test
    void getAllQuestion() {
        Mockito.when(questionRepository.findAll()).thenReturn(List.of(question1,question2));
        assertThat(questionService.getAllQuestion().size()).isEqualTo(2);
    }

    @Test
    void getQuestionById() {
        Mockito.when(questionRepository.findById(1l)).thenReturn(Optional.ofNullable(question1));
        assertThat(questionService.getQuestionById(1l).getId()).isEqualTo(1l);
    }

    @Test
    void deleteQuestion() {
        questionService.deleteQuestion(1l);
        verify(questionRepository,times(1)).deleteById(1l);
    }

    @Test
    void findByQuid() {
        Mockito.when(questionRepository.findByQuiz(quiz)).thenReturn(Set.of(question1,question2));
        assertThat(questionService.findByQuid(quiz).size()).isEqualTo(2);
    }
}