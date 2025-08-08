package com.bookworm.service.impl;

import com.bookworm.entities.Customer;
import com.bookworm.repository.CustomerRepository;
import com.bookworm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementation of the CustomerService interface.
 * This class contains the business logic for customer management.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        // In a real application, you would perform validation here.
        // For example, check if email, phone, or PAN already exist.
        // Also, the password should be hashed before saving.
        return customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer updateCustomer(Integer id, Customer customerDetails) {
        Customer existingCustomer = getCustomerById(id);

        // Update fields from the provided details
        existingCustomer.setName(customerDetails.getName());
        existingCustomer.setEmail(customerDetails.getEmail());
        // Note: Password updates should be handled carefully, usually via a separate endpoint.
        if (customerDetails.getPasswordHash() != null && !customerDetails.getPasswordHash().isEmpty()) {
            existingCustomer.setPasswordHash(customerDetails.getPasswordHash());
        }
        existingCustomer.setPhone(customerDetails.getPhone());
        existingCustomer.setDateOfBirth(customerDetails.getDateOfBirth());
        existingCustomer.setAge(customerDetails.getAge());
        existingCustomer.setAddress(customerDetails.getAddress());

        return customerRepository.save(existingCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new NoSuchElementException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
