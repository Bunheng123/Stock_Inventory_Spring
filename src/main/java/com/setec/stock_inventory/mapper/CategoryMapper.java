package com.setec.stock_inventory.mapper;

import com.setec.stock_inventory.dto.Request.CategoryRequestDto;
import com.setec.stock_inventory.dto.Response.CategoryResponseDto;
import com.setec.stock_inventory.entity.Category;

public class CategoryMapper {

    // take from database to response
    //Database → Category (entity) → toResponse() → CategoryResponseDto → sent to client
    public static CategoryResponseDto toResponse(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    // take data from request to response to entity
    public static Category toEntity(CategoryRequestDto request) {
        if (request == null) {
            return null;
        }

        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
