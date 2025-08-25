package com.bookworm.entities;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the type of a product (e.g., E-Book, Audiobook).
 * Maps to the 'product_types' table.
 */
@Entity
@Table(name = "product_type_master")
@Getter
@Setter
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer id;

    @Column(name = "type_desc", length = 255, nullable = false)
    private String description;

   
}
