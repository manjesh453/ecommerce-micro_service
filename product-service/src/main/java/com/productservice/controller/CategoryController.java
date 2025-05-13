package com.productservice.controller;


import com.productservice.dto.CategoryResponseDto;
import com.productservice.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category/")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getCategoryById/{categoryId}")
    public CategoryResponseDto getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping("/getAllCategory")
    public List<CategoryResponseDto> getAllCategory(){
        return categoryService.getAllCategory();
    }

}
