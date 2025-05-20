package com.cartservice.controller;

import com.cartservice.dto.*;
import com.cartservice.service.CartService;
import com.cartservice.shared.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public String addToCart(@RequestBody CartRequestDto requestDto){
        return cartService.addToCart(requestDto);
    }

    @PostMapping("/update/{cartId}")
    public String updateCart(@RequestBody UpdateDto quantity, @PathVariable Long cartId){
        return cartService.updateCart(quantity.getQuantity(), cartId);
    }

    @GetMapping("/delete/{cartId}")
    public String deleteCart(@PathVariable Long cartId){
        return cartService.deleteCart(cartId);
    }

    @GetMapping("/getAll")
    public List<CartResponseDto> getAllCart(){
        return cartService.getAllCarts();
    }

    @GetMapping("/getCartById/{cartId}")
    public CartResponseDto getCartById(@PathVariable Long cartId){
        return cartService.getCartById(cartId);
    }

    @GetMapping("/getCartByUserId/{userId}")
    public List<CartResponseDto> getCartByUserId(@PathVariable Long userId){
        return cartService.getCartByCustomerId(userId);
    }

    @PostMapping("/carts/byStatus/{customerId}")
    public List<CartResponseDto> getCartByUserIdAndStatus(@PathVariable Long customerId,@RequestBody StatusDto status){
        return cartService.getCartByCustomerIdAndStatus(customerId, status.getStatus());
    }
    @PostMapping("/carts/checkout")
    public String checkout(@RequestBody List<CheckOutDto> requestDtos){
        return cartService.checkout(requestDtos);
    }
}
