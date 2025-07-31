package com.bookworm.controller;

import com.bookworm.entities.Product;
import com.bookworm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * REST controller for managing Product entities.
 * Exposes endpoints for CRUD operations and searching for products.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * POST /api/products : Create a new product.
     *
     * @param product The product to create.
     * @return The ResponseEntity with status 201 (Created) and with body the new product.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    /**
     * GET /api/products : Get all products.
     *
     * @return The ResponseEntity with status 200 (OK) and the list of products in body.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * GET /api/products/search : Search for products by name.
     *
     * @param name The name query to search for.
     * @return The ResponseEntity with status 200 (OK) and the list of matching products.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String name) {
        List<Product> products = productService.searchByName(name);
        return ResponseEntity.ok(products);
    }

    /**
     * GET /api/products/{id} : Get the "id" product.
     *
     * @param id The id of the product to retrieve.
     * @return The ResponseEntity with status 200 (OK) and with body the product, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * PUT /api/products/{id} : Updates an existing product.
     *
     * @param id The id of the product to update.
     * @param productDetails The product with updated details.
     * @return The ResponseEntity with status 200 (OK) and with body the updated product,
     * or with status 404 (Not Found) if the product does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/products/{id} : Delete the "id" product.
     *
     * @param id The id of the product to delete.
     * @return The ResponseEntity with status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
