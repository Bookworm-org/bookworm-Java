package com.bookworm.controller;

import com.bookworm.entities.Customer;
import com.bookworm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * REST controller for managing Customer entities.
 * Exposes endpoints for CRUD operations on customers.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * POST /api/customers : Create a new customer.
     *
     * @param customer The customer to create.
     * @return The ResponseEntity with status 201 (Created) and with body the new customer.
     */
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    /**
     * GET /api/customers : Get all customers.
     *
     * @return The ResponseEntity with status 200 (OK) and the list of customers in body.
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * GET /api/customers/{id} : Get the "id" customer.
     *
     * @param id The id of the customer to retrieve.
     * @return The ResponseEntity with status 200 (OK) and with body the customer, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        try {
            Customer customer = customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * PUT /api/customers/{id} : Updates an existing customer.
     *
     * @param id The id of the customer to update.
     * @param customerDetails The customer with updated details.
     * @return The ResponseEntity with status 200 (OK) and with body the updated customer,
     * or with status 404 (Not Found) if the customer does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer customerDetails) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
            return ResponseEntity.ok(updatedCustomer);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/customers/{id} : Delete the "id" customer.
     *
     * @param id The id of the customer to delete.
     * @return The ResponseEntity with status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
