package com.cartservice.dto;

import lombok.Data;

@Data
public class OrderRequestDto {
    private String productName;
    private int quantity;
    private int amount;
    private String paymentDetail;
    private Long userId;
    private String imageName;
    private Long productId;
}
