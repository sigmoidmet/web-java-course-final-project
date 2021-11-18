package com.example.final_project.controller;

import com.example.final_project.exception.NotAvailableProductException;
import com.example.final_project.model.Product;
import com.example.final_project.pojo.Cart;
import com.example.final_project.pojo.CartItem;
import com.example.final_project.service.CartSessionService;
import com.example.final_project.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final CartSessionService cartSessionService;

    @GetMapping("products")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @PostMapping("cart")
    public void addItemToCart(@RequestBody CartItem cartItem, HttpSession httpSession) {
        modifyCartItemsQuantity(cartItem, httpSession);
    }

    @PutMapping("cart")
    public void modifyItemInCart(@RequestBody CartItem cartItem, HttpSession httpSession) {
        modifyCartItemsQuantity(cartItem, httpSession);
    }

    private void modifyCartItemsQuantity(CartItem cartItem, HttpSession httpSession) {
        if (productService.isAvailable(cartItem.getId(), cartItem.getQuantity())) {
            saveCartItemToSession(cartItem, httpSession);
        } else {
            throw new NotAvailableProductException(cartItem.getQuantity(), cartItem.getId());
        }
    }

    private void saveCartItemToSession(CartItem cartItem, HttpSession httpSession) {
        Cart cart = cartSessionService.getCartOrNew(httpSession);

        cart.removeItem(cartItem);
        cart.addItem(cartItem);

        cartSessionService.updateCartInSession(cart, httpSession);
    }

    @GetMapping("cart")
    public Cart getCart(HttpSession httpSession) {
        return cartSessionService.getCartOrNew(httpSession);
    }

    @DeleteMapping("cart/{itemId}")
    public Cart removeItemFromCart(@PathVariable Long itemId, HttpSession httpSession) {
        Cart cart = cartSessionService.getCartOrNew(httpSession);
        cart.getCartItems().remove(item(itemId));
        return cart;
    }

    private CartItem item(Long itemId) {
        CartItem cartItem = new CartItem();
        cartItem.setId(itemId);
        return cartItem;
    }
}
