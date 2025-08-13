package com.bookworm.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * Represents a single rented item in the user's temporary library.
 * It includes the crucial rental expiry date for display on the "MyLibrary" page.
 */
@Getter
@Setter
public class LibraryItemDTO {
    private Integer productId;
    private String productName;
    private String author;
    private String imageSource;
    private Date rentalDate;
    private Date rentalExpiryDate;
}
