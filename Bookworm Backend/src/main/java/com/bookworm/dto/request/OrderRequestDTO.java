package com.bookworm.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

// DTO for the incoming order request from the client
@Getter
@Setter
public class OrderRequestDTO {
    private Integer customerId;
    private List<OrderItemRequestDTO> items;
    // This can be expanded later to include shipping info, etc.
}