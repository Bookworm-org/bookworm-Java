package com.bookworm.service;

import com.bookworm.dto.request.OrderRequestDTO;
import com.bookworm.dto.response.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequest);
    
    // New method signature
    OrderResponseDTO createOrderFromCart(Integer customerId);
}