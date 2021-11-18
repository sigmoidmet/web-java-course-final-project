package com.example.final_project.service;

import com.example.final_project.pojo.Cart;

import javax.servlet.http.HttpSession;

public interface CartSessionService {

    Cart consumeCart(HttpSession httpSession);

    Cart getCartOrNew(HttpSession httpSession);

    void updateCartInSession(Cart cart, HttpSession httpSession);
}
