package com.setec.stock_inventory.service;

import java.util.List;

import com.setec.stock_inventory.dto.Request.OrderRequestDto;
import com.setec.stock_inventory.dto.Response.OrderResponseDto;

import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto request);

    OrderResponseDto getOrderById(Long id);

    List<OrderResponseDto> getAllOrders();

    List<OrderResponseDto> getOrdersByUserId(Long userId);

    OrderResponseDto updateStatus(Long id, String status);

}
