package com.orderservice.dto;

import lombok.Data;

@Data
public class OrderRequestDto {
    private String productName;
    private int quantity;
    private int amount;
    private String deliveryAddress;
    private Long receiverId;
    private String imageName;
}
