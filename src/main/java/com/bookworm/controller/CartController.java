package com.bookworm.controller;


import com.bookworm.entities.Cart;
import com.bookworm.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * REST controller for managing Cart entities.
 * Exposes endpoints for cart operations related to customers.
 */
@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * POST /api/carts/customer/{customerId} : Create a new active cart for a customer.
     *
     * @param customerId The ID of the customer.
     * @return The ResponseEntity with status 201 (Created) and with body the new cart.
     */
    @PostMapping("/customer/{customerId}")
    public ResponseEntity<Cart> createCartForCustomer(@PathVariable Integer customerId) {
        try {
            Cart newCart = cartService.createCart(customerId);
            return new ResponseEntity<>(newCart, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/carts/customer/{customerId}/active : Get the active cart for a customer.
     *
     * @param customerId The ID of the customer.
     * @return The ResponseEntity with status 200 (OK) and with body the active cart, or 404 if not found.
     */
    @GetMapping("/customer/{customerId}/active")
    public ResponseEntity<Cart> getActiveCart(@PathVariable Integer customerId) {
        Cart activeCart = cartService.getActiveCartByCustomerId(customerId);
        if (activeCart != null) {
            return ResponseEntity.ok(activeCart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/carts/customer/{customerId} : Get all carts for a customer.
     *
     * @param customerId The ID of the customer.
     * @return The ResponseEntity with status 200 (OK) and the list of carts.
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Cart>> getAllCartsByCustomer(@PathVariable Integer customerId) {
        List<Cart> carts = cartService.getAllCartsByCustomerId(customerId);
        return ResponseEntity.ok(carts);
    }

    /**
     * GET /api/carts/{id} : Get a cart by its specific ID.
     *
     * @param id The ID of the cart.
     * @return The ResponseEntity with status 200 (OK) and with body the cart, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Integer id) {
        try {
            Cart cart = cartService.getCartById(id);
            return ResponseEntity.ok(cart);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/carts/{id} : Delete a cart by its ID.
     *
     * @param id The ID of the cart to delete.
     * @return The ResponseEntity with status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Integer id) {
        try {
            cartService.deleteCart(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
