package com.bookworm.entities;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a language for products.
 * Maps to the 'languages' table.
 */
@Entity
@Table(name = "language_master")
@Getter
@Setter
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Integer id;

    @Column(name = "language_desc", length = 50) // Increased from 5 in ERD for practicality
    private String description;

}