package com.bookworm.service;

import com.bookworm.dto.request.CartItemRequestDTO;
import com.bookworm.dto.response.CartResponseDTO;

public interface CartService {
    CartResponseDTO addProductToCart(Integer customerId, CartItemRequestDTO requestDTO);
    CartResponseDTO removeProductFromCart(Integer customerId, Integer productId);
    CartResponseDTO getCartByCustomerId(Integer customerId);
}