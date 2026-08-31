package com.setec.stock_inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 100)
    private String name;

    private String description;

    private double price;

    private String imageUrl;

    private String publicId;

    @Min(value = 1 , message = "stock must have at least one")
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id" , nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orders;

}