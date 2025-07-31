package com.bookworm.service.impl;

import com.bookworm.entities.Product;
import com.bookworm.repository.ProductRepository;
import com.bookworm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementation of the ProductService interface.
 * This class contains the business logic for product management.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product addProduct(Product product) {
        // Additional validation can be performed here, e.g., check for existing ISBN
        return productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product updateProduct(Integer id, Product productDetails) {
        Product existingProduct = getProductById(id);

        // Update all relevant fields from the provided details
        existingProduct.setName(productDetails.getName());
        existingProduct.setEnglishName(productDetails.getEnglishName());
        existingProduct.setAuthor(productDetails.getAuthor());
        existingProduct.setIsbn(productDetails.getIsbn());
        existingProduct.setLongDescription(productDetails.getLongDescription());
        existingProduct.setShortDescription(productDetails.getShortDescription());
        existingProduct.setImageSource(productDetails.getImageSource());
        existingProduct.setRentable(productDetails.isRentable());
        existingProduct.setMinRentDays(productDetails.getMinRentDays());
        existingProduct.setRentPerDay(productDetails.getRentPerDay());
        existingProduct.setBasePrice(productDetails.getBasePrice());
        existingProduct.setOfferPrice(productDetails.getOfferPrice());
        existingProduct.setSpecialCost(productDetails.getSpecialCost());
        existingProduct.setGenre(productDetails.getGenre());
        existingProduct.setLanguage(productDetails.getLanguage());
        existingProduct.setProductType(productDetails.getProductType());

        return productRepository.save(existingProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
