package com.example.final_project.service;

import com.example.final_project.pojo.Cart;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class CartSessionServiceImpl implements CartSessionService {

    private final String SESSION_CART_KEY = "cart";

    @Override
    public Cart consumeCart(HttpSession httpSession) {
        Cart cart = getCartOrNew(httpSession);
        httpSession.removeAttribute(SESSION_CART_KEY);
        return cart;
    }

    @Override
    public Cart getCartOrNew(HttpSession httpSession) {
        return Optional.ofNullable(
                (Cart) httpSession.getAttribute(SESSION_CART_KEY)
        ).orElse(new Cart());
    }

    @Override
    public void updateCartInSession(Cart cart, HttpSession httpSession) {
        httpSession.setAttribute(SESSION_CART_KEY, cart);
    }
}
