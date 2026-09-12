package com.setec.stock_inventory.service.impl;


import com.setec.stock_inventory.dto.Request.OrderItemRequestDto;
import com.setec.stock_inventory.dto.Request.OrderRequestDto;
import com.setec.stock_inventory.dto.Response.OrderResponseDto;
import com.setec.stock_inventory.entity.Order;
import com.setec.stock_inventory.entity.OrderItem;
import com.setec.stock_inventory.entity.Product;
import com.setec.stock_inventory.entity.User;
import com.setec.stock_inventory.exception.BadRequestException;
import com.setec.stock_inventory.exception.ResourceNotFoundException;
import com.setec.stock_inventory.mapper.OrderMapper;
import com.setec.stock_inventory.repo.OrderItemRepository;
import com.setec.stock_inventory.repo.OrderRepository;
import com.setec.stock_inventory.repo.ProductRepository;
import com.setec.stock_inventory.repo.UserRepository;
import com.setec.stock_inventory.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto request) {
        //check user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find user with id: " + request.getUserId()));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setTotalAmount(0);

        double totalAmount = 0;

        List<OrderItemRequestDto> orderItems = request.getOrderItemList();
        List<OrderItem> orderItemEntityList = new ArrayList<>();

        for (OrderItemRequestDto storeOrderItem : orderItems) {
            Product product = productRepository.findById(storeOrderItem.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot find product with id: " + storeOrderItem.getProductId()));

            if (product.getStock() < storeOrderItem.getQuantity()) {
                throw new BadRequestException("Product stock is not enough for product: " + product.getName());
            }

            // new stock
            int newStock = product.getStock() - storeOrderItem.getQuantity();
            product.setStock(newStock);
            productRepository.save(product);

            double subtotal = product.getPrice() * storeOrderItem.getQuantity();

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(storeOrderItem.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);

            orderItemEntityList.add(orderItem);

            totalAmount += subtotal;
        }

        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItemEntityList);

        Order saved = orderRepository.save(order);

        return OrderMapper.toResponse(saved);
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findByIdWithDetails(id).orElseThrow(
            () -> new ResourceNotFoundException("Order not found with id " + id)
        );
        return OrderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAllWithDetails().stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    @Override
    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Cannot find user with id: " + userId);
        }
        return orderRepository.findByUserIdWithDetails(userId).stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public OrderResponseDto updateStatus(Long id, String status) {
        Order order = orderRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        String newStatus = status.trim().toUpperCase();
        // Optional: If cancelling an order, return the stock back to products
        if ("CANCELLED".equals(newStatus) && !"CANCELLED".equals(order.getStatus())) {
            for (OrderItem item : order.getOrderItems()) {
                Product product = item.getProduct();
                product.setStock(product.getStock() + item.getQuantity());
                productRepository.save(product);
            }
        }
        order.setStatus(newStatus);
        Order updated = orderRepository.save(order);
        return OrderMapper.toResponse(updated);
    }
}