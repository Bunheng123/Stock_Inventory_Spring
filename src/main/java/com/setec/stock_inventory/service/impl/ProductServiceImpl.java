package com.setec.stock_inventory.service.impl;

import com.setec.stock_inventory.config.CloudinaryService;
import com.setec.stock_inventory.dto.Request.ProductRequestDto;
import com.setec.stock_inventory.dto.Response.ProductResponseDto;
import com.setec.stock_inventory.entity.Category;
import com.setec.stock_inventory.entity.Product;
import com.setec.stock_inventory.exception.ResourceNotFoundException;
import com.setec.stock_inventory.mapper.ProductMapper;
import com.setec.stock_inventory.repo.CategoryRepository;
import com.setec.stock_inventory.repo.ProductRepository;
import com.setec.stock_inventory.service.ProductService;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service 
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;

    public ProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            CloudinaryService cloudinaryService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto request) {
         Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id " + request.getCategoryId())
        );

        String imageUrl = null;
        String publicId = null;

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            Map image = cloudinaryService.uploadFile(request.getFile());
            imageUrl = (String) image.get("url");
            publicId = (String) image.get("public_id");
        }

        Product product = ProductMapper.toEntity(request);
        product.setCategory(category);
        product.setImageUrl(imageUrl);
        product.setPublicId(publicId);

        Product saved = productRepository.save(product);
        return ProductMapper.toResponse(saved);

    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAllWithDetails().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with id " + id)
        );
        return ProductMapper.toResponse(product);
    }

    @Override
    public List<ProductResponseDto> getProductsByCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id " + categoryId);
        }
        return productRepository.findByCategoryId(categoryId).stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto request) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with id " + id)
        );

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id " + request.getCategoryId())
        );

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            if (product.getPublicId() != null) {
                cloudinaryService.deleteFile(product.getPublicId());
            }
            Map<?, ?> image = cloudinaryService.uploadFile(request.getFile());
            product.setImageUrl((String) image.get("url"));
            product.setPublicId((String) image.get("public_id"));
        }

        Product updated = productRepository.save(product);
        return ProductMapper.toResponse(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with id " + id)
        );

        if (product.getPublicId() != null) {
            cloudinaryService.deleteFile(product.getPublicId());
        }

        productRepository.delete(product);
    }
}
