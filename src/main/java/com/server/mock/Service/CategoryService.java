package com.server.mock.Service;

import com.server.mock.model.exam.Category;

import java.util.Set;

public interface CategoryService {
    public Category createCategory(Category category);
    public Category updateCategory(Category category);
    public Set<Category> getAllCategory();
    public Category getCategoryById(Long id);
    public void deleteCategory(Long id);
}
