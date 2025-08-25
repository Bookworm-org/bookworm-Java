package com.bookworm.service;


import com.bookworm.entities.ProductType;
import java.util.List;

/**
 * Service interface for managing ProductType entities.
 * Defines the business logic operations for product types.
 */
public interface ProductTypeService {

    /**
     * Saves a new product type or updates an existing one.
     *
     * @param productType The product type object to be saved.
     * @return The saved product type.
     */
    ProductType saveProductType(ProductType productType);

    /**
     * Retrieves a product type by its unique ID.
     *
     * @param id The ID of the product type to retrieve.
     * @return The found product type.
     */
    ProductType getProductTypeById(Integer id);

    /**
     * Retrieves all product types.
     *
     * @return A list of all product types.
     */
    List<ProductType> getAllProductTypes();

    /**
     * Deletes a product type by its ID.
     *
     * @param id The ID of the product type to delete.
     */
    void deleteProductType(Integer id);
}

