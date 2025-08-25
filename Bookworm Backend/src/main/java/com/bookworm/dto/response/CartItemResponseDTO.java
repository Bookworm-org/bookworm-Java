package com.bookworm.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemResponseDTO {
    private Integer productId;
    private String productName;
    private String author;
    private String imageSource;
    private String shortDescription; // Added field for short description
    private BigDecimal itemCost;
    private int quantity;
    private boolean isRented;
    private Integer rentNumberOfDays;
}