package com.setec.stock_inventory.dto.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class OrderItemRequestDto {

    @NotBlank(message = "Product Id is required")
    private Long productId;

    @NotBlank(message = "Quantity is required")
    @Min(value=1,message = "Quantity must be at least 1")
    private Integer quantity;
}
