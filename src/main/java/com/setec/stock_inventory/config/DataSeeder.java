package com.setec.stock_inventory.config;

import com.setec.stock_inventory.entity.User;
import com.setec.stock_inventory.enums.Role;
import com.setec.stock_inventory.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
            log.info("Default ADMIN user created: username='admin', password='admin123', role='ADMIN'");
        }

        if (!userRepository.existsByUsername("stock")) {
            User stock = User.builder()
                    .username("stock")
                    .email("stock@example.com")
                    .password(passwordEncoder.encode("stock123"))
                    .role(Role.STOCK)
                    .build();

            userRepository.save(stock);
            log.info("Default STOCK user created: username='stock', password='stock123', role='STOCK'");
        }
    }
}
