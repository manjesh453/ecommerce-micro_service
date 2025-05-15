package com.orderservice.service;

import com.orderservice.dto.OrderRequestDto;
import com.orderservice.dto.OrderResponseDto;
import com.orderservice.shared.Status;

import java.util.Date;
import java.util.List;

public interface OrderService {
    String createOrder(OrderRequestDto requestDto);
    String createBulkOrder(List<OrderRequestDto> requestDtoList);
    OrderResponseDto getOrder(Long orderId);
    List<OrderResponseDto> getAllOrdersByCustomer(Long customerId);
    List<OrderResponseDto> getAllOrder();
    List<OrderResponseDto> getAllOrdersByDate(Date startDate, Date endDate);
    String updateOrderStatus(Long orderId, Status status);
    List<OrderResponseDto> getAllOrdersByReceiverId(Long receiverId);
}
