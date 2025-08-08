package com.bookworm.repository;

import com.bookworm.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    /**
     * Finds a customer by their email address.
     * As email is unique, this will return at most one customer.
     *
     * @param email The email address to search for.
     * @return An Optional containing the found customer, or an empty Optional if not found.
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Finds a customer by their phone number.
     * As phone is unique, this will return at most one customer.
     *
     * @param phone The phone number to search for.
     * @return An Optional containing the found customer, or an empty Optional if not found.
     */
    Optional<Customer> findByPhone(String phone);

    /**
     * Finds a customer by their PAN card number.
     * As PAN is unique, this will return at most one customer.
     *
     * @param pan The PAN card number to search for.
     * @return An Optional containing the found customer, or an empty Optional if not found.
     */
}
