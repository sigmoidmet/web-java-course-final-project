package com.example.final_project.controller;

import com.example.final_project.model.Client;
import com.example.final_project.model.ProductOrder;
import com.example.final_project.pojo.Cart;
import com.example.final_project.pojo.OrderDto;
import com.example.final_project.service.CartSessionService;
import com.example.final_project.service.OrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final CartSessionService cartSessionService;

    @PostMapping("orders")
    public ProductOrder orderItems(@AuthenticationPrincipal UserDetails principal, HttpSession httpSession) {
        Cart cart = cartSessionService.consumeCart(httpSession);

        return orderService.orderItemsForClient(cart, principal.getUsername());
    }

    @DeleteMapping("orders/{orderId}")
    public void cancelOrder(@AuthenticationPrincipal UserDetails principal, @PathVariable("orderId") ProductOrder productOrder) {
        orderService.cancelOrderForClient(productOrder, principal.getUsername());
    }

    @GetMapping("orders")
    public List<OrderDto> getAll(@AuthenticationPrincipal UserDetails principal) {
        return orderService.getAllForClient(principal.getUsername());
    }
}
