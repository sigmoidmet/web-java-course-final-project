package com.example.final_project.service;

import com.example.final_project.model.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    List<Product> getAll();

    boolean isAvailable(Long productId, Long quantity);

    Product getById(Long productId);
}
