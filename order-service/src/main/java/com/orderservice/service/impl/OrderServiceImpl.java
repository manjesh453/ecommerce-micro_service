package com.orderservice.service.impl;

import com.orderservice.dto.OrderRequestDto;
import com.orderservice.dto.OrderResponseDto;
import com.orderservice.entity.Orders;
import com.orderservice.repo.OrderRepo;
import com.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final ModelMapper modelMapper;
    @Override
    public String createOrder(OrderRequestDto requestDto) {
        Orders order = modelMapper.map(requestDto, Orders.class);
        return "";
    }

    @Override
    public String createBulkOrder(List<OrderRequestDto> requestDtoList) {
        return "";
    }

    @Override
    public OrderResponseDto getOrder(Long orderId) {
        return null;
    }

    @Override
    public List<OrderResponseDto> getAllOrdersByCustomer(Long customerId) {
        return List.of();
    }

    @Override
    public List<OrderResponseDto> getAllOrder() {
        return List.of();
    }

    @Override
    public List<OrderResponseDto> getAllOrdersByDate(Date startDate, Date endDate) {
        return List.of();
    }
}
