package com.bookworm.entities;


import jakarta.persistence.*;
import lombok.*;
;

/**
 * Represents a genre for categorizing products.
 * Maps to the 'genres' table.
 */
@Entity
@Table(name = "genre_master")
@Getter
@Setter
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Integer id;

    @Column(name = "genre_desc", length = 25, nullable = false)
    private String description;

    
}