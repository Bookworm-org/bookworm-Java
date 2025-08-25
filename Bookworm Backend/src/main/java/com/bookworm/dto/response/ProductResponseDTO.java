package com.bookworm.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/*
 * DTO for sending Product data in responses.
 */
@Getter
@Setter
public class ProductResponseDTO {

    private Integer id;
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

    // Fields for related entities
    private Integer genreId;
    private String genreName;
    private Integer languageId;
    private String languageName;
    private Integer productTypeId;
    private String productTypeName;

}
