package com.setec.stock_inventory.repo;

import com.setec.stock_inventory.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
