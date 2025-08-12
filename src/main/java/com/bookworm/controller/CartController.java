package com.bookworm.controller;

import com.bookworm.dto.request.CartItemRequestDTO;
import com.bookworm.dto.response.CartResponseDTO;
import com.bookworm.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{customerId}")
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable Integer customerId) {
        CartResponseDTO cart = cartService.getCartByCustomerId(customerId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{customerId}/items")
    public ResponseEntity<CartResponseDTO> addProductToCart(
            @PathVariable Integer customerId,
            @RequestBody CartItemRequestDTO requestDTO) {
        CartResponseDTO updatedCart = cartService.addProductToCart(customerId, requestDTO);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{customerId}/items/{productId}")
    public ResponseEntity<CartResponseDTO> removeProductFromCart(
            @PathVariable Integer customerId,
            @PathVariable Integer productId) {
        CartResponseDTO updatedCart = cartService.removeProductFromCart(customerId, productId);
        return ResponseEntity.ok(updatedCart);
    }
}
