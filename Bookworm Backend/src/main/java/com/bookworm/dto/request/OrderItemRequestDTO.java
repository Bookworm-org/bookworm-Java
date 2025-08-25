package com.bookworm.dto.request;

import lombok.Getter;
import lombok.Setter;

// DTO for each item within the order request
@Getter
@Setter
public class OrderItemRequestDTO {
    private Integer productId;
    private String acquisitionType; // Will be "PURCHASE" or "RENTAL"
    private Integer rentalPeriodDays; // Optional: only for rentals
}