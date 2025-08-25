package com.bookworm.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

/**
 * A container DTO for the "MyShelf" page. It holds the list of
 * purchased items and provides summary data like total price and quantity.
 */
@Getter
@Setter
public class ShelfResponseDTO {
    private List<ShelfItemDTO> items;
    private int totalQuantity;
    private BigDecimal totalPrice;
}
