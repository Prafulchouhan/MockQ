package com.server.mock.controller;

import com.server.mock.Service.CategoryService;
import com.server.mock.model.exam.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin("*")
public class CategoryController {

    Logger logger= LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

//    @PostMapping
//    public ResponseEntity<Category> addCategory(@RequestBody Category category){
//        Category category1=this.categoryService.createCategory(category);
//        return new ResponseEntity<>(category1, HttpStatus.CREATED);
//    }

    @PostMapping
    public Category addCategory(@RequestBody Category category){
        Category category1=this.categoryService.createCategory(category);
        return category1;
    }

    @GetMapping
    public ResponseEntity<?> getAllCategory(){
        Set<Category> categories=this.categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public Category getCategoryById(@PathVariable("categoryId") Long id){
        logger.info("hi I am here");
        System.out.println("id**********    = " + id);
        Category category1=this.categoryService.getCategoryById(id);
        return category1;
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
        Category category1=this.categoryService.updateCategory(category);
        return new ResponseEntity<>(category1, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("categoryId") Long id){
        this.categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value="/users")
    public void method(@RequestBody MultiValueMap<String, String> values) {
        logger.info("******Values:{}", values);
    }
}
