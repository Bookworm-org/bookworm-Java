package com.bookworm.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a single purchased item on the user's permanent shelf.
 * It contains all the necessary details for display on the "MyShelf" page.
 */
@Getter
@Setter
public class ShelfItemDTO {
    private Integer productId;
    private String productName;
    private String author;
    private String imageSource;
    private Date purchaseDate;
    private int quantity = 1; // Assuming 1 for digital products, can be adjusted if needed
    private BigDecimal price; // The price the user paid for this specific item
}
