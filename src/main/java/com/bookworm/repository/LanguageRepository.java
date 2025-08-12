package com.bookworm.repository;


import com.bookworm.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Language entity.
 * Provides standard database operations for languages.
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    // Custom query methods can be added here if needed, for example:
    // Optional<Language> findByDescription(String description);
}
