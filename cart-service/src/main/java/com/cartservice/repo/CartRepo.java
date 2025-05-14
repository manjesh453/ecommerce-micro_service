package com.cartservice.repo;

import com.cartservice.entity.Cart;
import com.cartservice.shared.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    List<Cart> findByUserIdAndStatus(Long userId, Status status);
}
