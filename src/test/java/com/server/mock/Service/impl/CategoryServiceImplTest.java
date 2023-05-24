package com.server.mock.Service.impl;

import com.server.mock.model.exam.Category;
import com.server.mock.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    private Category category1,category2;
    private List<Category> categoryList;

    @BeforeEach
    public void setUp(){
        category1=new Category(1l,"title","desc",null);
        category2=new Category(1l,"title","desc",null);
        categoryList=Arrays.asList(category1,category2);
    }

    @Test
    void createCategory() {
        Mockito.when(categoryRepository.save(category1)).thenReturn(category1);
        assertThat(categoryService.createCategory(category1).getId()).isEqualTo(1l);
    }

    @Test
    void updateCategory() {
        Mockito.when(categoryRepository.save(category1)).thenReturn(category1);
        assertThat(categoryService.updateCategory(category1).getId()).isEqualTo(1l);
    }

    @Test
    void getAllCategory() {
        Mockito.when(categoryRepository.findAll()).thenReturn(categoryList);
        assertThat(categoryService.getAllCategory().size()).isEqualTo(1);
    }

    @Test
    void getCategoryById() {
        Mockito.when(categoryRepository.findById(1l)).thenReturn(Optional.of(category1));
        assertThat(categoryService.getCategoryById(1l).getId()).isEqualTo(1l);
    }

    @Test
    void deleteCategory() {
        categoryService.deleteCategory(1l);
        verify(categoryRepository,times(1)).deleteById(anyLong());
    }
}