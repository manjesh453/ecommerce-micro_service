package com.productservice.repo;


import com.productservice.entity.Product;
import com.productservice.shared.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByUserId(Long userId);

    List<Product> findByCategoryIdAndStatus (Long categoryId, Status status);

    @Query("SELECT p FROM Product p WHERE p.title LIKE %:keyword% ")
    List<Product>findByKeyword(String keyword);

    Page<Product> findAll(Pageable pageable);

    List<Product> findByStatus(Status status);
}
