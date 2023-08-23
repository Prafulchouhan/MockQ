package com.server.mock.Service.impl;

import com.server.mock.model.exam.Question;
import com.server.mock.model.exam.Quiz;
import com.server.mock.repository.QuizRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class QuizServiceImplTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizServiceImpl quizService;

    private Quiz quiz1,quiz2;

    @BeforeEach
    void setUp() {
        Question question1=new Question(1l,
                null,
                "question1",
                "image",
                "opt1",
                "opt1",
                "opt1",
                "opt1",
                "ans");
        Question question2=new Question(2l,
                null,
                "question1",
                "image",
                "opt1",
                "opt1",
                "opt1",
                "opt1",
                "ans");
        quiz1=new Quiz(1l,"title","desc","10","2",true,20,null,Set.of(question1,question2));
        quiz2=new Quiz(2l,"title","desc","100","20",false,10,null,Set.of(question1,question2));
    }

    @Test
    void createQuiz() {
        Mockito.when(quizRepository.save(quiz1)).thenReturn(quiz1);
        assertThat(quizService.createQuiz(quiz1).getId()).isEqualTo(1l);
    }

    @Test
    void updateQuiz() {
        Mockito.when(quizRepository.save(quiz1)).thenReturn(quiz2);
        assertThat(quizService.updateQuiz(quiz1).getId()).isEqualTo(2l);
    }

    @Test
    void getAllQuiz() {
        Mockito.when(quizRepository.findAll()).thenReturn(List.of(quiz1,quiz2));
        assertThat(quizService.getAllQuiz().size()).isEqualTo(2);
    }

    @Test
    void getQuizById() {
        Mockito.when(quizRepository.findById(1l)).thenReturn(Optional.ofNullable(quiz1));
        assertThat(quizService.getQuizById(1l).getTitle()).isEqualTo("title");
    }

    @Test
    void getQuizByCategoryId() {
        Mockito.when(quizRepository.findByCategory_Id(1l)).thenReturn(Optional.of(List.of(quiz1,quiz2)));
        assertThat(quizService.getQuizByCategoryId(1l).size()).isEqualTo(2);
    }

    @Test
    void deleteQuiz() {
        quizService.deleteQuiz(1l);
        verify(quizRepository,times(1)).deleteById(anyLong());
    }
}