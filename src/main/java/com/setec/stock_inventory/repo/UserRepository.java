package com.setec.stock_inventory.repo;

import com.setec.stock_inventory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
