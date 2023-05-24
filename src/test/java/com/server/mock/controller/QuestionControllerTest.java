package com.server.mock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.mock.Service.QuestionService;
import com.server.mock.Service.QuizService;
import com.server.mock.model.exam.Question;
import com.server.mock.model.exam.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuestionControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private QuestionController questionController;

    @Mock
    private QuestionService questionService;

    @Mock
    private QuizService quizService;

    private Question question1,question2;
    private Quiz quiz;

    @BeforeEach
    void setUp() {
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

        quiz=new Quiz(1l,"title","desc","100","20",true,20,null,Set.of(question1,question2));

        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(questionController).build();
    }

    @Test
    void addQuestion() throws Exception {
        doReturn(question1).when(questionService).createQuestion(any());
        String content=new ObjectMapper().writeValueAsString(question1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/question")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$",notNullValue()));
    }

    @Test
    void updateQuestion() throws Exception {
        doReturn(question2).when(questionService).updateQuestion(any());
        String content=new ObjectMapper().writeValueAsString(question2);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/question")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()));
    }

    @Test
    void getAllQuestion() throws Exception {
        doReturn(Set.of(question1,question2)).when(questionService).getAllQuestion();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/question")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$",hasSize(2)));
    }

    @Test
    void getQuestionById() throws Exception {
        doReturn(question2).when(questionService).getQuestionById(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/question/{question_id}",1l)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.id",is(2)));
    }

    @Test
    void getQuestionsOfQuiz() throws Exception {
        doReturn(quiz).when(quizService).getQuizById(1l);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/question/quiz/{quiz_id}",1l)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$",hasSize(2)));
        Mockito.verify(quizService,times(1)).getQuizById(1l);
    }

    @Test
    void getQuestionsOfQuizAdmin() throws Exception {
        doReturn(quiz).when(quizService).getQuizById(1l);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/question/quiz/all/{quiz_id}",1l)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$",hasSize(2)));
        Mockito.verify(quizService,times(1)).getQuizById(1l);
    }

    @Test
    void deleteQuestionById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/question/{id}",1l))
                .andExpect(status().isOk());
        Mockito.verify(questionService,times(1)).deleteQuestion(1l);
    }
}