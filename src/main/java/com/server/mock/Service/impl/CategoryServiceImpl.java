package com.server.mock.Service.impl;

import com.server.mock.Service.CategoryService;
import com.server.mock.model.exam.Category;
import com.server.mock.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.LogManager;


@CacheConfig(cacheNames = "Category")
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @CachePut(key = "#category.id",value = "Category")
    public Category createCategory(Category category) {
        System.out.println("category = " + category);
        return this.categoryRepository.save(category);
    }

    @Override
    @Cacheable(key = "#category.id",value = "Category")
    public Category updateCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    @Cacheable( value = "AllCategories")
    public Set<Category> getAllCategory() {
        return new LinkedHashSet<>( this.categoryRepository.findAll() );
    }

    @Override
    @Cacheable(key = "#id",value = "Category")
    public Category getCategoryById(Long id) {
        return this.categoryRepository.findById(id).get();
    }

    @Override
    @CacheEvict(key = "#id",value = "Category")
    public void deleteCategory(Long id) {
        this.categoryRepository.deleteById(id);
    }
}
