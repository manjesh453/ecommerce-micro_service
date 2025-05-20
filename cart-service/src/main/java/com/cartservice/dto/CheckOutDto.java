package com.cartservice.dto;

import lombok.Data;

@Data
public class CheckOutDto {
    public Long userId;
    private String name;
    private int quantity;
    private String imageName;
    private float price;
    private int amount;
    public Long productId;
    private String paymentDetail;
}
