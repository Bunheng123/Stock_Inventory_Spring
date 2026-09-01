package com.setec.stock_inventory.mapper;

import com.setec.stock_inventory.dto.Request.ProductRequestDto;
import com.setec.stock_inventory.dto.Response.ProductResponseDto;
import com.setec.stock_inventory.entity.Product;

public class ProductMapper {

    public static ProductResponseDto toResponse(Product product) {
        if (product == null) {
            return null;
        }

        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .publicId(product.getPublicId())
                .stock(product.getStock())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();
    }

    public static Product toEntity(ProductRequestDto request){
        if(request == null){
            return null;
        }

        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice() != null ? request.getPrice() : 0.0)
                .stock(request.getStock() != null ? request.getStock() : 0)
                .build();
    }

}
