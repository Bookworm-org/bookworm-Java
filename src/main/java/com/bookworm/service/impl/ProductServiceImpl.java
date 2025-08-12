package com.bookworm.service.impl;


import com.bookworm.dto.request.ProductRequestDTO;
import com.bookworm.dto.response.ProductResponseDTO;
import com.bookworm.entities.Genre;
import com.bookworm.entities.Language;
import com.bookworm.entities.Product;
import com.bookworm.entities.ProductType;
import com.bookworm.repository.GenreRepository;
import com.bookworm.repository.LanguageRepository;
import com.bookworm.repository.ProductRepository;
import com.bookworm.repository.ProductTypeRepository;
import com.bookworm.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/*
 * Implementation of the ProductService interface.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final GenreRepository genreRepository;
    private final LanguageRepository languageRepository;
    private final ProductTypeRepository productTypeRepository;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Product product = new Product();
        mapDtoToEntity(requestDTO, product);
        Product savedProduct = productRepository.save(product);
        return toResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO updateProduct(Integer id, ProductRequestDTO requestDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
        mapDtoToEntity(requestDTO, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return toResponseDTO(updatedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
        return toResponseDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findProductsByAuthor(String authorName) {
        List<Product> products = productRepository.findByAuthorContainingIgnoreCase(authorName);
        return products.stream()
                .map(this::toResponseDTO) // Reuses your existing mapping method
                .collect(Collectors.toList());
    }
    
    /*
     * Maps fields from a DTO to an entity, including fetching related entities.
     */
    private void mapDtoToEntity(ProductRequestDTO dto, Product product) {
        product.setName(dto.getName());
        product.setEnglishName(dto.getEnglishName());
        product.setAuthor(dto.getAuthor());
        product.setIsbn(dto.getIsbn());
        product.setLongDescription(dto.getLongDescription());
        product.setShortDescription(dto.getShortDescription());
        product.setImageSource(dto.getImageSource());
        product.setRentable(dto.isRentable());
        product.setMinRentDays(dto.getMinRentDays());
        product.setRentPerDay(dto.getRentPerDay());
        product.setBasePrice(dto.getBasePrice());
        product.setOfferPrice(dto.getOfferPrice());
        product.setSpecialCost(dto.getSpecialCost());

        // Fetch and set related entities
        Genre genre = genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new NoSuchElementException("Genre not found with id: " + dto.getGenreId()));
        product.setGenre(genre);

        Language language = languageRepository.findById(dto.getLanguageId())
                .orElseThrow(() -> new NoSuchElementException("Language not found with id: " + dto.getLanguageId()));
        product.setLanguage(language);

        ProductType productType = productTypeRepository.findById(dto.getProductTypeId())
                .orElseThrow(() -> new NoSuchElementException("ProductType not found with id: " + dto.getProductTypeId()));
        product.setProductType(productType);
    }

    /*
     * Simple mapper method to convert a Product entity to a response DTO.
     */
    private ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setEnglishName(product.getEnglishName());
        dto.setAuthor(product.getAuthor());
        dto.setIsbn(product.getIsbn());
        dto.setLongDescription(product.getLongDescription());
        dto.setShortDescription(product.getShortDescription());
        dto.setImageSource(product.getImageSource());
        dto.setRentable(product.isRentable());
        dto.setMinRentDays(product.getMinRentDays());
        dto.setRentPerDay(product.getRentPerDay());
        dto.setBasePrice(product.getBasePrice());
        dto.setOfferPrice(product.getOfferPrice());
        dto.setSpecialCost(product.getSpecialCost());

        // Map related entity info
        dto.setGenreId(product.getGenre().getId());
        dto.setGenreName(product.getGenre().getDescription());
        dto.setLanguageId(product.getLanguage().getId());
        dto.setLanguageName(product.getLanguage().getDescription());
        dto.setProductTypeId(product.getProductType().getId());
        dto.setProductTypeName(product.getProductType().getDescription());

        return dto;
    }

}
