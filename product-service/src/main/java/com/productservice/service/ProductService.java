package com.productservice.service;



import com.productservice.dto.ProductRequestDto;
import com.productservice.dto.ProductResponseDto;
import com.productservice.shared.Status;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    String createProduct(ProductRequestDto postDto, String fileName);

    String updateProduct(ProductRequestDto postDto, String fileName, Long postId) throws IOException;

    String deleteProduct(Long postId);

    List<ProductResponseDto> getAllProduct();

    ProductResponseDto getProductById(Long postId);

    List<ProductResponseDto> getProductByCategoryForUsers(Long categoryId);

    List<ProductResponseDto> getProductByCategoryForAdmin(Long categoryId, Status status);

    List<ProductResponseDto> getProductByUser(Long userId);

    List<ProductResponseDto> searchProductByTitle(String keyword);

    List<ProductResponseDto> getProductForUnauthorizedUser();

    String decreaseProductQuantity(Long productId, int quantity);
}
