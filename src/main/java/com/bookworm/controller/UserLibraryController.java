package com.bookworm.controller;

import com.bookworm.dto.response.LibraryResponseDTO;
import com.bookworm.dto.response.ShelfResponseDTO;
import com.bookworm.service.UserLibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-collection") // A new, clear mapping for this feature
@RequiredArgsConstructor
public class UserLibraryController {

    private final UserLibraryService userLibraryService;

    /**
     * 
     * Endpoint to get a user's permanent collection of purchased books ("MyShelf").
     * Returns a list of items along with the total quantity and total price paid.
     *
     * @param customerId The ID of the customer.
     * @return A DTO containing the user's shelf details.
     */
    @GetMapping("/{customerId}/shelf")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ShelfResponseDTO> getMyShelf(@PathVariable Integer customerId) {
        // You should add a security check here to ensure the authenticated user
        // can only access their own shelf.
        ShelfResponseDTO shelfResponse = userLibraryService.getShelfForCustomer(customerId);
        return ResponseEntity.ok(shelfResponse);
    }

    /*
     * Endpoint to get a user's collection of actively rented books ("MyLibrary").
     * Returns a list of items, each with its rental expiry date.
     *
     * @param customerId The ID of the customer.
     * @return A DTO containing the user's rental library details.
     */
    @GetMapping("/{customerId}/library")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LibraryResponseDTO> getMyLibrary(@PathVariable Integer customerId) {
        // You should add a security check here to ensure the authenticated user
        // can only access their own library.
        LibraryResponseDTO libraryResponse = userLibraryService.getLibraryForCustomer(customerId);
        return ResponseEntity.ok(libraryResponse);
    }
}
