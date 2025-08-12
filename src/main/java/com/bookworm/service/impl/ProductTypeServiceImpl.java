package com.bookworm.service.impl;


import com.bookworm.entities.ProductType;
import com.bookworm.repository.ProductTypeRepository;
import com.bookworm.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementation of the ProductTypeService interface.
 * This class contains the business logic for product type management.
 */
@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    @Transactional
    public ProductType saveProductType(ProductType productType) {
        return productTypeRepository.save(productType);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductType getProductTypeById(Integer id) {
        return productTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ProductType not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteProductType(Integer id) {
        if (!productTypeRepository.existsById(id)) {
            throw new NoSuchElementException("ProductType not found with id: " + id);
        }
        productTypeRepository.deleteById(id);
    }
}

