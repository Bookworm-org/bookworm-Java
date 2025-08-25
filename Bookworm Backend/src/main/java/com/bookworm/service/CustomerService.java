package com.bookworm.service;

import com.bookworm.entities.Customer;
import java.util.List;


public interface CustomerService {

   
    Customer saveCustomer(Customer customer);

    /**
     * Retrieves a customer by their unique ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The found customer, or null if no customer exists with the given ID.
     */
    Customer getCustomerById(Integer id);

    /**
     * Retrieves all registered customers.
     *
     * @return A list of all customers.
     */
    List<Customer> getAllCustomers();

    /**
     * Updates the details of an existing customer.
     *
     * @param id The ID of the customer to update.
     * @param customerDetails The new details for the customer.
     * @return The updated customer object.
     */
    Customer updateCustomer(Integer id, Customer customerDetails);

    /**
     * Deletes a customer by their ID.
     *
     * @param id The ID of the customer to delete.
     */
    void deleteCustomer(Integer id);
}
