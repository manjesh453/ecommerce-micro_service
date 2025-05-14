package com.cartservice.controller;

import com.cartservice.dto.CartRequestDto;
import com.cartservice.dto.CartResponseDto;
import com.cartservice.service.CartService;
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
    public String updateCart(@RequestBody int quantity,@PathVariable Long cartId){
        return cartService.updateCart(quantity,cartId);
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
    public List<CartResponseDto> getCartByUserIdAndStatus(@PathVariable Long customerId,@RequestBody String status){
        return cartService.getCartByCustomerIdAndStatus(customerId,status);
    }
}
