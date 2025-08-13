package com.bookworm.service.impl;

import com.bookworm.dto.response.LibraryItemDTO;
import com.bookworm.dto.response.LibraryResponseDTO;
import com.bookworm.dto.response.ShelfItemDTO;
import com.bookworm.dto.response.ShelfResponseDTO;
import com.bookworm.entities.InvoiceDetail;
import com.bookworm.entities.Product;
import com.bookworm.entities.UserLibrary;
import com.bookworm.repository.InvoiceDetailRepository;
import com.bookworm.repository.ProductRepository;
import com.bookworm.repository.RentalLedgerRepository;
import com.bookworm.repository.UserLibraryRepository;
import com.bookworm.service.UserLibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserLibraryServiceImpl implements UserLibraryService {

    private final UserLibraryRepository userLibraryRepository;
    private final RentalLedgerRepository rentalLedgerRepository;
    private final ProductRepository productRepository;
    private final InvoiceDetailRepository invoiceDetailRepository;

    @Override
    public ShelfResponseDTO getShelfForCustomer(Integer customerId) {
        // 1. Find all active purchased entitlements for the customer
        List<UserLibrary> purchasedEntries = userLibraryRepository.findByCustomerIdAndAcquisitionTypeAndStatus(
                customerId, "PURCHASE", "ACTIVE");

        // 2. Convert each entitlement into a detailed DTO
        List<ShelfItemDTO> shelfItems = purchasedEntries.stream()
                .map(this::mapToShelfItemDTO)
                .collect(Collectors.toList());

        // 3. Calculate the total price from the collected items
        BigDecimal totalPrice = shelfItems.stream()
                .map(ShelfItemDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. Assemble the final response object
        ShelfResponseDTO response = new ShelfResponseDTO();
        response.setItems(shelfItems);
        response.setTotalQuantity(shelfItems.size());
        response.setTotalPrice(totalPrice);

        return response;
    }

    @Override
    public LibraryResponseDTO getLibraryForCustomer(Integer customerId) {
        // 1. Find all active rental entitlements for the customer
        List<UserLibrary> rentedEntries = userLibraryRepository.findByCustomerIdAndAcquisitionTypeAndStatus(
                customerId, "RENTAL", "ACTIVE");

        // 2. Convert each rental entitlement into a detailed DTO
        List<LibraryItemDTO> libraryItems = rentedEntries.stream()
                .map(this::mapToLibraryItemDTO)
                .collect(Collectors.toList());

        // 3. Assemble the final response object
        LibraryResponseDTO response = new LibraryResponseDTO();
        response.setItems(libraryItems);
        response.setTotalItems(libraryItems.size());

        return response;
    }

    private ShelfItemDTO mapToShelfItemDTO(UserLibrary entry) {
        ShelfItemDTO dto = new ShelfItemDTO();
        Product product = getProductOrThrow(entry.getProductId());
        
        // Find the original transaction to get the price the user actually paid
        InvoiceDetail invoiceDetail = invoiceDetailRepository.findById(entry.getInvoiceDetailId())
                .orElseThrow(() -> new IllegalStateException("Data integrity error: InvoiceDetail not found for ID " + entry.getInvoiceDetailId()));

        dto.setProductId(entry.getProductId());
        dto.setProductName(product.getName());
        dto.setAuthor(product.getAuthor());
        dto.setImageSource(product.getImageSource());
        dto.setPurchaseDate(entry.getAcquisitionTimestamp());
        dto.setPrice(invoiceDetail.getSellPrice()); // Set the exact price from the invoice
        return dto;
    }

    private LibraryItemDTO mapToLibraryItemDTO(UserLibrary entry) {
        LibraryItemDTO dto = new LibraryItemDTO();
        Product product = getProductOrThrow(entry.getProductId());

        dto.setProductId(entry.getProductId());
        dto.setProductName(product.getName());
        dto.setAuthor(product.getAuthor());
        dto.setImageSource(product.getImageSource());
        dto.setRentalDate(entry.getAcquisitionTimestamp());

        // Find the rental details to get the expiry date
        rentalLedgerRepository.findById(entry.getUserLibraryId())
                .ifPresent(ledger -> dto.setRentalExpiryDate(ledger.getRentEndDate()));

        return dto;
    }

    private Product getProductOrThrow(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Data integrity error: Product not found for ID " + productId));
    }
}
