package com.cartservice.service;

import com.cartservice.dto.CartRequestDto;
import com.cartservice.dto.CartResponseDto;
import com.cartservice.dto.CheckOutDto;

import java.util.List;

public interface CartService {

    String addToCart(CartRequestDto requestDto);
    String updateCart(int quantity,Long cartId);
    String deleteCart(Long cartId);
    CartResponseDto getCartById(Long cartId);
    List<CartResponseDto> getAllCarts();
    List<CartResponseDto> getCartByCustomerId(Long customerId);
    List<CartResponseDto> getCartByCustomerIdAndStatus(Long customerId,String status);
    String checkout(List<CheckOutDto> requestDtoList);
}
