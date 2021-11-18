package com.example.final_project.pojo;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Cart {

    private Set<CartItem> cartItems = new HashSet<>();

    public void addItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }

    public void removeItem(CartItem cartItem) {
        cartItems.remove(cartItem);
    }
}
