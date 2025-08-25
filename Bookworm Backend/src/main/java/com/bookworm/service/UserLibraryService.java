package com.bookworm.service;

import com.bookworm.dto.response.LibraryResponseDTO;
import com.bookworm.dto.response.ShelfResponseDTO;

public interface UserLibraryService {

    /**
     * Fetches all permanently purchased books for a given customer,
     * including summary data like total quantity and price.
     * This corresponds to the "MyShelf" view.
     *
     * @param customerId The ID of the customer.
     * @return A DTO containing the list of purchased items and summary totals.
     */
    ShelfResponseDTO getShelfForCustomer(Integer customerId);

    /**
     * Fetches all actively rented books for a given customer.
     * This corresponds to the "MyLibrary" view.
     *
     * @param customerId The ID of the customer.
     * @return A DTO containing the list of rented items and a total count.
     */
    LibraryResponseDTO getLibraryForCustomer(Integer customerId);
}