package com.bookworm.repository;

import com.bookworm.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * Spring Data JPA repository for the Product entity.
 * Provides standard database operations and custom query methods for products.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /*
     * Finds a product by its unique ISBN.
     *
     * @param isbn The ISBN to search for.
     * @return An Optional containing the found product, or an empty Optional if not found.
     */
    List<Product> findByGenreId(Integer genreId);

    /*
     * Finds all products whose name contains the given string, case-insensitively.
     *
     * @param name The string to search for within product names.
     * @return A list of matching products.
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /*
     * Finds all products by a specific author.
     *
     * @param author The author's name to search for.
     * @return A list of products by the given author.
     */
    List<Product> findByAuthorContainingIgnoreCase(String author);
    

    /*
     * Finds all products that are available for rent.
     *
     * @param isRentable The rentable status to filter by.
     * @return A list of products that match the rentable status.
     */
    List<Product> findByIsRentable(boolean isRentable);
    
    List<Product> findByLanguageId(Integer languageId);


}
