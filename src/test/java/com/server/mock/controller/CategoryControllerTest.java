package com.server.mock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.mock.Service.CategoryService;
import com.server.mock.Service.impl.CategoryServiceImpl;
import com.server.mock.model.exam.Category;
import lombok.With;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryServiceImpl categoryService;

    private Category category1,category2;

    @BeforeEach
    void setUp() {
        category1=new Category(1l,"title","desc",null);
        category2=new Category(1l,"title","desc",null);
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void testAddCategory() throws Exception {
        String content = new ObjectMapper().writeValueAsString(category1);
//        when(categoryService.createCategory(category1)).thenReturn(category1);
        doReturn(category1).when(categoryService).createCategory(any());
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testGetAllCategory() throws Exception {
        when(categoryService.getAllCategory()).thenReturn(Set.of(category1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/category")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testGetCategoryById() throws Exception {
        when(categoryService.getCategoryById(anyLong())).thenReturn(category1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/category/{id}",1l)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testUpdateCategory() throws Exception {
        String content = new ObjectMapper().writeValueAsString(category1);
//        when(categoryService.createCategory(category1)).thenReturn(category1);
        doReturn(category2).when(categoryService).updateCategory(any());
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()));
    }

    @Test
    void testDeleteCategoryById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/category/{categoryId}", 1l)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
         Mockito.verify(categoryService, Mockito.times(1)).deleteCategory(1l);
    }
}