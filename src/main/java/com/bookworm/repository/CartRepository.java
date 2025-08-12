package com.bookworm.repository;


import com.bookworm.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Cart entity.
 * Provides standard database operations and custom queries for carts.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    /**
     * Finds all carts associated with a specific customer ID.
     *
     * @param customerId The ID of the customer.
     * @return A list of carts belonging to the customer.
     */
    List<Cart> findByCustomerId(Integer customerId);

    /**
     * Finds a cart by customer ID and its active status.
     * This is useful for retrieving the current active cart for a customer.
     *
     * @param customerId The ID of the customer.
     * @param isActive   The active status of the cart.
     * @return An Optional containing the found cart, or an empty Optional if not found.
     */
    Optional<Cart> findByCustomerIdAndIsActive(Integer customerId, boolean isActive);
}
