package com.bookworm.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a product in the catalog, such as a book or e-book.
 * This is a central entity linking to descriptions, pricing, genres, and more.
 * Maps to the 'products' table.
 */
@Entity
@Table(name = "product_master")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;

    @Column(name = "product_name", length = 255, nullable = false)
    private String name;

    @Column(name = "english_name", length = 255)
    private String englishName;

    @Column(name = "product_author", length = 255)
    private String author;

    @Column(name = "product_isbn", length = 20, unique = true)
    private String isbn;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "long_description", columnDefinition = "TEXT")
    private String longDescription;

    @Column(name = "short_description", length = 255) // Corrected from 25 to 255
    private String shortDescription;

    @Column(name = "img_src", length = 255)
    private String imageSource;

    @Column(name = "is_rentable")
    private boolean isRentable;

    @Column(name = "min_rent_days")
    private Integer minRentDays;

    @Column(name = "rent_per_day", precision = 10, scale = 2)
    private BigDecimal rentPerDay;

    @Column(name = "product_base_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal basePrice;

    @Column(name = "product_offer_price", precision = 10, scale = 2)
    private BigDecimal offerPrice;

    @Column(name = "product_sp_cost", precision = 10, scale = 2)
    private BigDecimal specialCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private ProductType productType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductAttribute> attributes;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductBeneficiary> beneficiaries;

    // Relationships to transactional details are typically not managed via cascade from Product
    @OneToMany(mappedBy = "product")
    private Set<InvoiceDetail> invoiceDetails;

    @OneToMany(mappedBy = "product")
    private Set<CartDetail> cartDetails;

    @OneToMany(mappedBy = "product")
    private Set<RentDetail> rentDetails;

}