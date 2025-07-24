package com.example.backend.controller;

import com.example.backend.model.Product;
import com.example.backend.repository.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> all(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return productRepository.findByTitleContainingIgnoreCase(search);
        }
        return productRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }
}
