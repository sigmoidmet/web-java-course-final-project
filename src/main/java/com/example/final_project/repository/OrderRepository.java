package com.example.final_project.repository;

import com.example.final_project.model.ProductOrder;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<ProductOrder, Long> {

    List<ProductOrder> findAllByClientEmail(String email);
}
