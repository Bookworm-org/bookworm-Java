package com.bookworm.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

// DTO for each item within the order response
@Getter
@Setter
public class OrderItemResponseDTO {
    private Integer productId;
    private String productName;
    private String acquisitionType;
    private Double price;
    private Date rentalEndDate; // Null for purchases
}
