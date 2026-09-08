package com.setec.stock_inventory.service;

import com.setec.stock_inventory.dto.Request.ProductRequestDto;
import com.setec.stock_inventory.dto.Response.ProductResponseDto;

import java.util.List;


public interface ProductService {
    
    ProductResponseDto createProduct(ProductRequestDto request);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getProductById(Long id);

    List<ProductResponseDto> getProductsByCategoryId(Long categoryId);

    ProductResponseDto updateProduct(Long id, ProductRequestDto request);

    void deleteProduct(Long id);

}