package com.orderservice.repo;

import com.orderservice.entity.Orders;
import com.orderservice.shared.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {
    List<Orders> findByUserIdAndStatus(Long receiverId, Status status);
    List<Orders> findByCreatedDateBetween(Date from, Date to);
    List<Orders> findByUserId(Long receiverId);
}
