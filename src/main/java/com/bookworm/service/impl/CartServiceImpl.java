package com.bookworm.service.impl;


import com.bookworm.entities.Cart;
import com.bookworm.entities.Customer;
import com.bookworm.repository.CartRepository;
import com.bookworm.repository.CustomerRepository;
import com.bookworm.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * Implementation of the CartService interface.
 * Contains the business logic for shopping cart management.
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Cart createCart(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found with id: " + customerId));

        // Deactivate any existing active carts for this customer
        cartRepository.findByCustomerIdAndIsActive(customerId, true).ifPresent(activeCart -> {
            activeCart.setActive(false);
            cartRepository.save(activeCart);
        });

        // Create a new active cart
        Cart newCart = new Cart();
        newCart.setCustomer(customer);
        newCart.setActive(true);
        newCart.setCost(BigDecimal.ZERO); // Initialize cost to zero
        return cartRepository.save(newCart);
    }

    @Override
    @Transactional(readOnly = true)
    public Cart getCartById(Integer id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cart not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Cart getActiveCartByCustomerId(Integer customerId) {
        return cartRepository.findByCustomerIdAndIsActive(customerId, true)
                .orElse(null); // Return null or throw exception based on business rules
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cart> getAllCartsByCustomerId(Integer customerId) {
        return cartRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional
    public void deleteCart(Integer id) {
        if (!cartRepository.existsById(id)) {
            throw new NoSuchElementException("Cart not found with id: " + id);
        }
        cartRepository.deleteById(id);
    }
}

