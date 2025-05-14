package com.cartservice.dto;

import lombok.Data;

@Data
public class CartRequestDto {
    public Long userId;
    private String name;
    private int quantity;
    private String imageName;
    private float price;
}
