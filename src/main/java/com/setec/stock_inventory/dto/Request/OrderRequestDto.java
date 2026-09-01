package com.setec.stock_inventory.dto.Request;

import com.setec.stock_inventory.entity.OrderItem;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class OrderRequestDto {

    @NotBlank(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Order Item must not empty")
    private List<OrderItemRequestDto> orderItemList;
}
