package com.cartservice.dto;

import com.cartservice.shared.Status;
import lombok.Data;

@Data
public class CartResponseDto {
    public Long userId;
    private String id;
    private String name;
    private int quantity;
    private String imageName;
    private float price;
    private Status status;
}
