package com.productservice.controller;


import com.productservice.dto.ProductRequestDto;
import com.productservice.dto.ProductResponseDto;
import com.productservice.service.FileService;
import com.productservice.service.ProductService;
import com.productservice.shared.Status;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/add")
    public String createPost(@RequestParam("image") MultipartFile image, @RequestPart ProductRequestDto postDto) throws IOException {
        String filename = fileService.uploadImage(path, image);
        return productService.createProduct(postDto, filename);
    }

    @PostMapping("/update/{productId}")
    public String updatePost(@RequestPart ProductRequestDto requestDto, @RequestParam("image") MultipartFile image,
                             @PathVariable Long productId) throws IOException {
        String filename = this.fileService.uploadImage(path, image);
        return productService.updateProduct(requestDto, filename, productId);
    }

    @GetMapping("/delete/{productId}")
    public String deletePost(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/getById/{productId}")
    public ProductResponseDto getPostById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("/getProductByCategoryForUser/{categoryId}")
    public List<ProductResponseDto> getPostByCategoryForUsers(@PathVariable Long categoryId) {
        return productService.getProductByCategoryForUsers(categoryId);
    }

    @GetMapping("/getProductByCategoryForAdmin/{categoryId}")
    public List<ProductResponseDto> getPostByCategoryForAdmin(@PathVariable Long categoryId, @RequestBody String status) {
        return productService.getProductByCategoryForAdmin(categoryId, Status.valueOf(status));
    }

    @GetMapping("/getProductByUser/{userId}")
    public List<ProductResponseDto> getPostByUser(@PathVariable Long userId) {
        return productService.getProductByUser(userId);
    }

    @GetMapping("/filterByTitle/{keyword}")
    public List<ProductResponseDto> filterByTitle(@PathVariable String keyword) {
        return productService.searchProductByTitle(keyword);
    }

    @GetMapping(value = "/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE )
    public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response)throws IOException {
        InputStream resource=this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

    @GetMapping("/productForUnauthorized")
    public List<ProductResponseDto> getPostForUnauthorizedUser() {
        return productService.getProductForUnauthorizedUser();
    }

    @GetMapping("/getAllProducts")
    public List<ProductResponseDto> getAllProducts(){
        return productService.getAllProduct();
    }
}
