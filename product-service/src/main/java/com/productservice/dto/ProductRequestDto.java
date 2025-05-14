package com.productservice.dto;

import lombok.Data;

@Data
public class ProductRequestDto {
    private String title;

    private String content;

    private Long userId;

    private Long categoryId;

    private int quantity;

    private float price;
}
