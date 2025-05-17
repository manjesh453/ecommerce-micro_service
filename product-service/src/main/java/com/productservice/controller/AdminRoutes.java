package com.productservice.controller;


import com.productservice.dto.CategoryRequestDto;
import com.productservice.dto.CategoryResponseDto;
import com.productservice.service.CategoryService;
import com.productservice.shared.Status;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AdminRoutes {
    private final CategoryService categoryService;

    @PostMapping("/category/add")
    public String addCategory(@RequestBody CategoryRequestDto requestDto) {
        return categoryService.createCategory(requestDto);
    }

    @PostMapping("/category/update/{categoryId}")
    public String updateCategory(@RequestBody CategoryRequestDto requestDto,@PathVariable Long categoryId) {
        return categoryService.updateCategory(requestDto,categoryId);
    }

    @GetMapping("/category/delete/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/category/getCategoryByStatus/{status}")
    public List<CategoryResponseDto> getCategoryByStatus(@PathVariable String status){
        return categoryService.getAllCategoryByStatus(Status.valueOf(status));
    }
}
