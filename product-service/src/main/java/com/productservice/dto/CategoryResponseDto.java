package com.productservice.dto;


import com.productservice.shared.Status;
import lombok.Data;

@Data
public class CategoryResponseDto {
    private Long id;
    private String categoryTitle;
    private String categoryDescription;
    private Status status;
}
