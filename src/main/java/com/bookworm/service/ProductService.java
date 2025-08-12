package com.bookworm.service;



import java.util.List;

import com.bookworm.dto.request.ProductRequestDTO;
import com.bookworm.dto.response.ProductResponseDTO;

/*
 * Service interface for managing Product entities.
 */
public interface ProductService {

    /*
     * Creates a new product.
     * @param requestDTO The DTO containing the data for the new product.
     * @return The created product as a response DTO.
     */
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);

    /*
     * Updates an existing product.
     * @param id The ID of the product to update.
     * @param requestDTO The DTO containing the updated data.
     * @return The updated product as a response DTO.
     */
    ProductResponseDTO updateProduct(Integer id, ProductRequestDTO requestDTO);

    /*
     * Retrieves a product by its ID.
     * @param id The ID of the product to retrieve.
     * @return The found product as a response DTO.
     */
    ProductResponseDTO getProductById(Integer id);

    /*
     * Retrieves all products.
     * @return A list of all products as response DTOs.
     */
    List<ProductResponseDTO> getAllProducts();

    /*
     * Deletes a product by its ID.
     * @param id The ID of the product to delete.
     */
    void deleteProduct(Integer id);
    
    List<ProductResponseDTO> findProductsByAuthor(String authorName);


}
