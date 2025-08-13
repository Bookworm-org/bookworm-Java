package com.bookworm.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestDTO {

    private Integer productId;
    
    private int quantity = 1; // Added quantity field, defaults to 1

    // This annotation explicitly tells the JSON parser that the
    // JSON property "rented" should be mapped to this "isRented" field.
    // This resolves the ambiguity and fixes the bug.
    @JsonProperty("isRented")
    private boolean isRented;

    private Integer rentNumberOfDays;
}
