package com.orderservice.controller;

import com.orderservice.dto.OrderRequestDto;
import com.orderservice.dto.OrderResponseDto;
import com.orderservice.service.OrderService;
import com.orderservice.shared.Status;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestBody OrderRequestDto order) {
        return orderService.createOrder(order);
    }

    @PostMapping("/createBulkOrder")
    public String placeBulkOrder(@RequestBody List<OrderRequestDto> order) {
        return orderService.createBulkOrder(order);
    }

    @GetMapping("/getOrderById/{orderId}")
    public OrderResponseDto getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping("/admin/getAllOrder")
    public List<OrderResponseDto> getOrders() {
        return orderService.getAllOrder();
    }

    @GetMapping("/getOrderByCustomer")
    public List<OrderResponseDto> getOrdersByCustomerId(@PathVariable Long customerId) {
        return orderService.getAllOrdersByCustomer(customerId);
    }

    @PostMapping("/admin/filterByDate")
    public List<OrderResponseDto> filterOrderByDate(@RequestBody Date from,@RequestBody Date to){
        return orderService.getAllOrdersByDate(from,to);
    }

    @GetMapping("/getOrderOfUser/{receiverId}")
    public List<OrderResponseDto> getAllOrdersByReceiverId(@PathVariable Long receiverId){
        return orderService.getAllOrdersByReceiverId(receiverId);
    }

    @PostMapping("/updateStatus/{orderId}")
    public String updateStatus(@PathVariable Long orderId,@RequestBody Status status) {
        return orderService.updateOrderStatus(orderId,status);
    }

}
