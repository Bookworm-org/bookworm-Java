package com.bookworm.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * A container DTO for the "MyLibrary" page. It holds the list of
 * all actively rented items and the total count.
 */
@Getter
@Setter
public class LibraryResponseDTO {
    private List<LibraryItemDTO> items;
    private int totalItems;
}
