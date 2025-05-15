package com.orderservice.service.impl;

import com.orderservice.dto.OrderRequestDto;
import com.orderservice.dto.OrderResponseDto;
import com.orderservice.entity.Orders;
import com.orderservice.exception.DataNotFoundException;
import com.orderservice.repo.OrderRepo;
import com.orderservice.service.OrderService;
import com.orderservice.shared.Status;
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
        order.setStatus(Status.ORDER_PLACED);
        orderRepo.save(order);
        return "Order has been placed successfully";
    }

    @Override
    public String createBulkOrder(List<OrderRequestDto> requestDtoList) {
        for(OrderRequestDto orderRequestDto : requestDtoList){
            Orders order = modelMapper.map(orderRequestDto, Orders.class);
            order.setStatus(Status.ORDER_PLACED);
            orderRepo.save(order);
        }
        return "Bulk Order has been placed successfully";
    }

    @Override
    public OrderResponseDto getOrder(Long orderId) {
       Orders order = orderRepo.findById(orderId).orElseThrow(()->
               new DataNotFoundException("Order with id " + orderId + " not found"));
       return modelMapper.map(order, OrderResponseDto.class);
    }

    @Override
    public List<OrderResponseDto> getAllOrdersByCustomer(Long customerId) {
        List<Orders> orders = orderRepo.findByReceiverIdAndStatus(customerId,Status.ORDER_PLACED);
        return orders.stream().map(li->modelMapper.map(li,OrderResponseDto.class)).toList();

    }

    @Override
    public List<OrderResponseDto> getAllOrder() {
        List<Orders> orders = orderRepo.findAll();
        return orders.stream().map(li->modelMapper.map(li,OrderResponseDto.class)).toList();
    }

    @Override
    public List<OrderResponseDto> getAllOrdersByDate(Date startDate, Date endDate) {
        List<Orders> orders = orderRepo.findByCreatedDate(startDate, endDate);
        return orders.stream().map(li->modelMapper.map(li,OrderResponseDto.class)).toList();

    }

    @Override
    public String updateOrderStatus(Long orderId, Status status) {
        Orders order = orderRepo.findById(orderId).orElseThrow(()->
                new DataNotFoundException("Order with id " + orderId + " not found"));
        order.setStatus(status);
        orderRepo.save(order);
        return "Status has been updated successfully to " + status.name();
    }

    @Override
    public List<OrderResponseDto> getAllOrdersByReceiverId(Long receiverId) {
        List<Orders> orders = orderRepo.findByReceiverId(receiverId);
        return orders.stream().map(li->modelMapper.map(li,OrderResponseDto.class)).toList();

    }
}
