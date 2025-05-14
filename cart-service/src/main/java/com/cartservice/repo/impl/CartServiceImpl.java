package com.cartservice.repo.impl;

import com.cartservice.dto.CartRequestDto;
import com.cartservice.dto.CartResponseDto;
import com.cartservice.entity.Cart;
import com.cartservice.exception.ResourcenotFoundException;
import com.cartservice.repo.CartRepo;
import com.cartservice.service.CartService;
import com.cartservice.shared.Status;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepo cartRepository;
    private final ModelMapper modelMapper;
    @Override
    public String addToCart(CartRequestDto requestDto) {
        Cart cart = modelMapper.map(requestDto, Cart.class);
        cart.setStatus(Status.ACTIVE);
        cartRepository.save(cart);
        return "You have successfully added to cart";
    }

    @Override
    public String updateCart(int quantity, Long cartId) {
        Cart cart=getCartByCartId(cartId);
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        return "You have added quantity to the cart";
    }

    @Override
    public String deleteCart(Long cartId) {
        Cart cart=getCartByCartId(cartId);
        cart.setStatus(Status.DELETED);
        cartRepository.save(cart);
        return "You have deleted from cart";
    }

    @Override
    public CartResponseDto getCartById(Long cartId) {
        return modelMapper.map(getCartByCartId(cartId), CartResponseDto.class);
    }

    @Override
    public List<CartResponseDto> getAllCarts() {
        List<Cart> carts=cartRepository.findAll();
        return carts.stream().map(li->modelMapper.map(li, CartResponseDto.class)).toList();
    }

    @Override
    public List<CartResponseDto> getCartByCustomerId(Long customerId) {
        List<Cart> carts=cartRepository.findByUserId(customerId);
        return carts.stream().map(li->modelMapper.map(li, CartResponseDto.class)).toList();
    }

    @Override
    public List<CartResponseDto> getCartByCustomerIdAndStatus(Long customerId, String status) {
        List<Cart> carts=cartRepository.findByUserIdAndStatus(customerId,Status.valueOf(status));
        return carts.stream().map(li->modelMapper.map(li, CartResponseDto.class)).toList();

    }

    private Cart getCartByCartId(Long id) {
        return cartRepository.findById(id).orElseThrow(()->
                new ResourcenotFoundException("Cart","cartId",id));
    }
}
