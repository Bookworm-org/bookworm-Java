package com.bookworm.repository;


import com.bookworm.entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductType entity.
 * Provides standard database operations for product types.
 */
@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    // Custom query methods can be defined here if necessary.
}

