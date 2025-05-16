package com.productservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.productservice.dto.CategoryResponseDto;
import com.productservice.dto.ProductRequestDto;
import com.productservice.dto.ProductResponseDto;
import com.productservice.entity.Product;
import com.productservice.exception.DataNotFoundException;
import com.productservice.exception.ResourcenotFoundException;
import com.productservice.repo.ProductRepo;
import com.productservice.service.CategoryService;
import com.productservice.service.FileService;
import com.productservice.service.ProductService;
import com.productservice.shared.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;
    private final FileService fileService;
    @Value("${project.image}")
    private String path;

    @Override
    public String createProduct(ProductRequestDto productDto, String fileName) {
        CategoryResponseDto category = categoryService.getCategory(productDto.getCategoryId());
        if (category == null) {
            throw new DataNotFoundException("Category not found");
        }
        Product post = objectMapper.convertValue(productDto, Product.class);
        post.setImageName(fileName);
        post.setStatus(Status.ACTIVE);
        productRepo.save(post);
        return "Product has been successfully created";
    }

    @Override
    public String updateProduct(ProductRequestDto productDto, String fileName, Long productId) throws IOException {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourcenotFoundException("Product", "productId", productId));
        if (product.getUserId() != productDto.getUserId()) {
           throw new DataNotFoundException("You do not have permission to update this product");
        }
        fileService.deleteImage(path,product.getImageName());
        product.setTitle(productDto.getTitle());
        product.setContent(productDto.getContent());
        product.setImageName(fileName);
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        productRepo.save(product);
        return "Product has been successfully updated";
    }

    @Override
    public String deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourcenotFoundException("Product", "productId", productId));
        product.setStatus(Status.DELETED);
        productRepo.save(product);
        return "Post has been successfully deleted";
    }

    @Override
    public List<ProductResponseDto> getAllProduct() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(list -> modelMapper.map(list, ProductResponseDto.class)).toList();
    }

    @Override
    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourcenotFoundException("Product", "productId", productId));
        return objectMapper.convertValue(product,ProductResponseDto.class);
    }

    @Override
    public List<ProductResponseDto> getProductByCategoryForUsers(Long categoryId) {
        List<Product> products = productRepo.findByCategoryIdAndStatus(categoryId,Status.ACTIVE);
        return products.stream().map(list -> modelMapper.map(list, ProductResponseDto.class)).toList();
    }

    @Override
    public List<ProductResponseDto> getProductByCategoryForAdmin(Long categoryId, Status status) {
        List<Product> products = productRepo.findByCategoryIdAndStatus(categoryId,status);
        return products.stream().map(list -> modelMapper.map(list, ProductResponseDto.class)).toList();
    }

    @Override
    public List<ProductResponseDto> getProductByUser(Long userId) {
        List<Product> products = productRepo.findByUserId(userId);
        return products.stream().map(list -> modelMapper.map(list, ProductResponseDto.class)).toList();
    }

    @Override
    public List<ProductResponseDto> searchProductByTitle(String keyword) {
        List<Product> products = productRepo.findByKeyword(keyword);
        return products.stream().map(list -> modelMapper.map(list, ProductResponseDto.class)).toList();
    }


    @Override
    public List<ProductResponseDto> getProductForUnauthorizedUser() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Product> products = productRepo.findAll(pageRequest);
        return products.stream().map(list -> modelMapper.map(list, ProductResponseDto.class)).toList();

    }

    @Override
    public String decreaseProductQuantity(Long productId, int quantity) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourcenotFoundException("Product", "productId", productId));
        if(product.getQuantity()>=quantity){
            product.setQuantity(product.getQuantity() - quantity);
            productRepo.save(product);
            return "Product has been successfully decreased";
        }
        return "Sorry product out of stock";
    }
}
