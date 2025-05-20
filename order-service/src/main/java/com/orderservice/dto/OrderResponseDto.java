package com.orderservice.dto;

import lombok.Data;

@Data
public class OrderResponseDto {
    private String id;
    private String productName;
    private int quantity;
    private int amount;
    private String paymentDetail;
    private Long userId;
    private String imageName;
    private String status;
}
