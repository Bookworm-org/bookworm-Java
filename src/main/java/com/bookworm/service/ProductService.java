package com.bookworm.service;

import com.bookworm.entities.Product;
import java.util.List;

/**
 * Service interface for managing Product entities.
 * Defines the business logic operations for products in the catalog.
 */
public interface ProductService {

    /**
     * Adds a new product to the catalog.
     *
     * @param product The product object to be saved.
     * @return The newly created product.
     */
    Product addProduct(Product product);

    /**
     * Retrieves a product by its unique ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The found product.
     */
    Product getProductById(Integer id);

    /**
     * Retrieves all products in the catalog.
     *
     * @return A list of all products.
     */
    List<Product> getAllProducts();

    /**
     * Updates the details of an existing product.
     *
     * @param id The ID of the product to update.
     * @param productDetails The new details for the product.
     * @return The updated product object.
     */
    Product updateProduct(Integer id, Product productDetails);

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     */
    void deleteProduct(Integer id);

    /**
     * Searches for products by name.
     *
     * @param name The name to search for.
     * @return A list of products matching the name.
     */
    List<Product> searchByName(String name);
}
