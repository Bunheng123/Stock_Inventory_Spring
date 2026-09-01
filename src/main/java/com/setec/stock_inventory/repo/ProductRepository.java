package com.setec.stock_inventory.repo;

import com.setec.stock_inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
