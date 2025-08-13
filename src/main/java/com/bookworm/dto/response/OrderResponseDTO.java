package com.bookworm.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

// DTO for the response sent back to the client after creating an order
@Getter
@Setter
public class OrderResponseDTO {
    private Long invoiceId;
    private Integer customerId;
    private Date orderDate;
    private Double totalAmount;
    private String status;
    private List<OrderItemResponseDTO> items;
}