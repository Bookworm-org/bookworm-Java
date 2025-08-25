package com.bookworm.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * DTO for receiving Product data in create/update requests.
 */
@Getter
@Setter
public class ProductRequestDTO {

    private String name;
    private String englishName;
    private String author;
    private String isbn;
    private String longDescription;
    private String shortDescription;
    private String imageSource;
    private boolean isRentable;
    private Integer minRentDays;
    private BigDecimal rentPerDay;
    private BigDecimal basePrice;
    private BigDecimal offerPrice;
    private BigDecimal specialCost;
    private Integer genreId;
    private Integer languageId;
    private Integer productTypeId;

}
