package com.setec.stock_inventory.dto.Request;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    @Size(max = 100 , message = "Product name must be less than 100 characters")
    private String name;

    @Size(max = 1000 , message = "description must be less than 1000 characters")
    private String description;

    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0" , inclusive = false , message = "price must be greater than 0")
    private double price;

    @NotNull(message = "Stock is required")
    @Min(value = 1, message = "Stock must have at least 1")
    private Integer stock;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    private MultipartFile file;


}
