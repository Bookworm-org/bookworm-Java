package com.bookworm.repository;

import com.bookworm.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Genre entity.
 * Provides standard database operations for genres.
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    // Custom query methods can be added here if needed.
}
