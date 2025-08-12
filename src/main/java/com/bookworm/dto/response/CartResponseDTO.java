package com.bookworm.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponseDTO {
    private Integer cartId;
    private Integer customerId;
    private BigDecimal totalCost;
    private int totalItems; // Added field for total quantity of items
    private List<CartItemResponseDTO> items;
}
