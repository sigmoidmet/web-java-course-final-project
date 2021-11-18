package com.example.final_project.service;

import com.example.final_project.model.ProductOrder;
import com.example.final_project.pojo.Cart;
import com.example.final_project.pojo.OrderDto;

import java.util.List;

public interface OrderService {

    ProductOrder orderItemsForClient(Cart cart, String email);

    void cancelOrderForClient(ProductOrder productOrder, String email);

    List<OrderDto> getAllForClient(String email);
}
