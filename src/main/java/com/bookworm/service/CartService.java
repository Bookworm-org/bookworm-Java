package com.bookworm.service;


import com.bookworm.entities.Cart;
import java.util.List;

/*
 * Service interface for managing Cart entities.
 * Defines the business logic for shopping cart operations.
 */
public interface CartService {

    /*
     * Creates a new cart for a given customer.
     * If the customer already has an active cart, it may be deactivated.
     *
     * @param customerId The ID of the customer for whom to create the cart.
     * @return The newly created cart.
     */
    Cart createCart(Integer customerId);

    /*
     * Retrieves a cart by its unique ID.
     *
     * @param id The ID of the cart to retrieve.
     * @return The found cart.
     */
    Cart getCartById(Integer id);

    /*
     * Retrieves the currently active cart for a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return The active cart, or null if none is found.
     */
    Cart getActiveCartByCustomerId(Integer customerId);

    /*
     * Retrieves all carts (both active and inactive) for a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of all carts for the customer.
     */
    List<Cart> getAllCartsByCustomerId(Integer customerId);

    /*
     * Deletes a cart by its ID.
     *
     * @param id The ID of the cart to delete.
     */
    void deleteCart(Integer id);
}
