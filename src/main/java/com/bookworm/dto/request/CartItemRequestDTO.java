package com.bookworm.dto.request;

import lombok.Data;

@Data
public class CartItemRequestDTO {
    private Integer productId;
    private int quantity;
    private boolean isRented = false;
    private Integer rentNumberOfDays;
}