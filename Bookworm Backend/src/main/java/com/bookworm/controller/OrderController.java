package com.bookworm.controller;

import com.bookworm.dto.request.OrderRequestDTO;
import com.bookworm.dto.response.OrderResponseDTO;
import com.bookworm.entities.Customer;
import com.bookworm.repository.CustomerRepository;
import com.bookworm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerRepository customerRepository; // Inject repository for security check

    /**
     * Creates a new order based on items provided directly in the request body.
     * This endpoint is useful for programmatic order creation.
     * Mapped to: POST /api/orders/create
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequest, Authentication authentication) {
        // Security Check: Ensure the user is creating an order for themselves.
        if (!isAuthorizedCustomer(authentication, orderRequest.getCustomerId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        OrderResponseDTO createdOrder = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    /**
     * Creates a new order directly from the items in a customer's active shopping cart.
     * This is the primary endpoint for a standard user checkout flow.
     * Mapped to: POST /api/orders/create-from-cart/{customerId}
     *
     * @param customerId The ID of the customer whose cart is being checked out.
     * @return A ResponseEntity containing the details of the created order.
     */
    @PostMapping("/create-from-cart/{customerId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponseDTO> createOrderFromCart(@PathVariable Integer customerId, Authentication authentication) {
        // Security Check: Ensure the authenticated user can only check out their own cart.
        if (!isAuthorizedCustomer(authentication, customerId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        OrderResponseDTO createdOrder = orderService.createOrderFromCart(customerId);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    /**
     * Helper method to verify that the authenticated user matches the customer ID in the request.
     *
     * @param authentication The security context's Authentication object.
     * @param customerId The customer ID from the request path or body.
     * @return true if the user is authorized, false otherwise.
     */
    private boolean isAuthorizedCustomer(Authentication authentication, Integer customerId) {
        String currentUsername = authentication.getName(); // This is the user's email/username from the JWT
        Optional<Customer> customerOpt = customerRepository.findById(customerId);

        if (customerOpt.isEmpty()) {
            return false; // Customer not found, deny access
        }

        return customerOpt.get().getEmail().equals(currentUsername);
    }
}
