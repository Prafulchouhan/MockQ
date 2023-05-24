package com.server.mock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.mock.Service.QuizService;
import com.server.mock.model.exam.Category;
import com.server.mock.model.exam.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuizControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private QuizController quizController;
    @Mock
    private QuizService quizService;

    private Quiz quiz1,quiz2;

    @BeforeEach
    void setUp() {
        quiz1=new Quiz(1l,"title1","desc","10","2",true,20,new Category(1l,"title","desc",null),null);
        quiz2=new Quiz(2l,"title2","desc","100","20",false,10,null,null);


        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(quizController).build();

        }

    @Test
    void addQuiz() throws Exception {
//        when(quizService.createQuiz(quiz1)).thenReturn(quiz1);
        doReturn(quiz1).when(quizService).createQuiz(any());
        String content= new ObjectMapper().writeValueAsString(quiz1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/quiz")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$",notNullValue()));
    }

    @Test
    void updateQuiz() throws Exception {
        when(quizService.updateQuiz(any())).thenReturn(quiz2);
        String content= new ObjectMapper().writeValueAsString(quiz2);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/quiz")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.id",is(2)));
    }

    @Test
    void getAllQuiz() throws Exception {
        when(quizService.getAllQuiz()).thenReturn(Set.of(quiz1,quiz2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/quiz")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$",hasSize(2)));
    }

    @Test
    void getQuizById() throws Exception {
        when(quizService.getQuizById(anyLong())).thenReturn(quiz2);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/quiz/{id}",2l)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.id",is(2)));
    }

    @Test
    void getQuizByCategoryId() throws Exception {
        when(quizService.getQuizByCategoryId(anyLong())).thenReturn(List.of(quiz1));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/quiz/category/{category_id}",1l)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].title",is("title1")));
    }

    @Test
    void deleteQuizById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/quiz/{quiz_id}",1l))
                .andExpect(status().isOk());
        Mockito.verify(quizService,times(1)).deleteQuiz(1l);
    }
}