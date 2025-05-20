package com.cartservice.service.impl;

import com.cartservice.dto.CartRequestDto;
import com.cartservice.dto.CartResponseDto;
import com.cartservice.dto.CheckOutDto;
import com.cartservice.dto.OrderRequestDto;
import com.cartservice.entity.Cart;
import com.cartservice.exception.ResourcenotFoundException;
import com.cartservice.repo.CartRepo;
import com.cartservice.service.CartService;
import com.cartservice.shared.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepo cartRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Override
    public String addToCart(CartRequestDto requestDto) {
        Cart cart = objectMapper.convertValue(requestDto, Cart.class);
        cart.setId(null);
        cart.setStatus(Status.ACTIVE);
        cartRepository.saveAndFlush(cart);
        return "You have successfully added to cart";
    }

    @Override
    public String updateCart(int quantity, Long cartId) {
        Cart cart = getCartByCartId(cartId);
        cart.setQuantity(quantity);
        cartRepository.saveAndFlush(cart);
        return "You have added quantity to the cart";
    }

    @Override
    public String deleteCart(Long cartId) {
        Cart cart = getCartByCartId(cartId);
        cart.setStatus(Status.DELETED);
        cartRepository.saveAndFlush(cart);
        return "You have deleted from cart";
    }

    @Override
    public CartResponseDto getCartById(Long cartId) {
        return modelMapper.map(getCartByCartId(cartId), CartResponseDto.class);
    }

    @Override
    public List<CartResponseDto> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().map(li -> modelMapper.map(li, CartResponseDto.class)).toList();
    }

    @Override
    public List<CartResponseDto> getCartByCustomerId(Long customerId) {
        List<Cart> carts = cartRepository.findByUserIdAndStatus(customerId, Status.ACTIVE);
        return carts.stream().map(li -> modelMapper.map(li, CartResponseDto.class)).toList();
    }

    @Override
    public List<CartResponseDto> getCartByCustomerIdAndStatus(Long customerId, String status) {
        List<Cart> carts = cartRepository.findByUserIdAndStatus(customerId, Status.valueOf(status));
        return carts.stream().map(li -> modelMapper.map(li, CartResponseDto.class)).toList();

    }

    @Override
    public String checkout(List<CheckOutDto> requestDtoList) {
        List<OrderRequestDto> orderRequestDtos = new ArrayList<>();
        for (CheckOutDto requestDto : requestDtoList) {
            Cart cart = cartRepository.findByUserIdAndNameAndStatus(requestDto.userId,
                    requestDto.getName(), Status.ACTIVE);
            cart.setStatus(Status.DELETED);
            cartRepository.saveAndFlush(cart);
            OrderRequestDto orderRequestDto = objectMapper.convertValue(requestDto, OrderRequestDto.class);
            orderRequestDto.setAmount(requestDto.getAmount());
            orderRequestDtos.add(orderRequestDto);
        }
        return createOrder(orderRequestDtos);
    }

    private String createOrder(List<OrderRequestDto> requestDto) {
        String token = (String) RequestContextHolder.currentRequestAttributes()
                .getAttribute("token", RequestAttributes.SCOPE_REQUEST);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<List<OrderRequestDto>> httpEntity = new HttpEntity<>(requestDto, headers);
        String url = "http://localhost:8080/api/order/createBulkOrder";
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                String.class
        );
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody().equals("Sorry product out of stock")) {
            return "Cannot place order";
        }
        return "Order has been placed successfully";

    }

    private Cart getCartByCartId(Long id) {
        return cartRepository.findById(id).orElseThrow(() ->
                new ResourcenotFoundException("Cart", "cartId", id));
    }
}
