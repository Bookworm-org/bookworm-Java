package com.bookworm.service.impl;

import com.bookworm.dto.request.OrderItemRequestDTO;
import com.bookworm.dto.request.OrderRequestDTO;
import com.bookworm.dto.response.OrderItemResponseDTO;
import com.bookworm.dto.response.OrderResponseDTO;
import com.bookworm.entities.*;
import com.bookworm.repository.*;
import com.bookworm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final InvoiceRepository invoiceRepository;
    private final UserLibraryRepository userLibraryRepository;
    private final RentalLedgerRepository rentalLedgerRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository; // Add CartRepository

    /**
     * New method to handle checkout directly from the cart.
     */
    @Override
    @Transactional
    public OrderResponseDTO createOrderFromCart(Integer customerId) {
        // 1. Fetch the user's active cart
        Cart activeCart = cartRepository.findByCustomerIdAndIsActiveTrue(customerId)
                .orElseThrow(() -> new NoSuchElementException("No active cart found for customer id: " + customerId));

        if (activeCart.getCartDetails() == null || activeCart.getCartDetails().isEmpty()) {
            throw new IllegalStateException("Cannot create an order from an empty cart.");
        }

        // 2. Transform CartDetails into OrderItemRequestDTOs
        List<OrderItemRequestDTO> orderItems = activeCart.getCartDetails().stream()
                .map(cartDetail -> {
                    OrderItemRequestDTO itemDto = new OrderItemRequestDTO();
                    itemDto.setProductId(cartDetail.getProduct().getId());
                    if (cartDetail.isRented()) {
                        itemDto.setAcquisitionType("RENTAL");
                        itemDto.setRentalPeriodDays(cartDetail.getRentNumberOfDays());
                    } else {
                        itemDto.setAcquisitionType("PURCHASE");
                    }
                    return itemDto;
                })
                .collect(Collectors.toList());

        // 3. Create the OrderRequestDTO to pass to our existing logic
        OrderRequestDTO orderRequest = new OrderRequestDTO();
        orderRequest.setCustomerId(customerId);
        orderRequest.setItems(orderItems);

        // 4. Call the existing createOrder method to reuse its logic
        OrderResponseDTO createdOrder = this.createOrder(orderRequest);

        // 5. Deactivate or clear the cart after successful checkout
        activeCart.setActive(false);
        cartRepository.save(activeCart);

        return createdOrder;
    }


    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {
        // ... your existing createOrder logic remains exactly the same ...
        // (The full implementation from the previous response goes here)
        customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + orderRequest.getCustomerId()));

        Invoice invoice = new Invoice();
        invoice.setCustomerId(orderRequest.getCustomerId());
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<InvoiceDetail> invoiceDetails = new ArrayList<>();

        for (OrderItemRequestDTO itemDto : orderRequest.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + itemDto.getProductId()));
            
            InvoiceDetail detail = new InvoiceDetail();
            detail.setProductId(product.getId());
            detail.setQuantity(1);
            detail.setInvoice(invoice);

            BigDecimal itemPrice;

            if ("SALE".equalsIgnoreCase(itemDto.getAcquisitionType()) || "PURCHASE".equalsIgnoreCase(itemDto.getAcquisitionType())) {
                detail.setTranType("SALE");
                itemPrice = (product.getOfferPrice() != null && product.getOfferPrice().compareTo(BigDecimal.ZERO) > 0)
                        ? product.getOfferPrice()
                        : product.getBasePrice();
            } else if ("RENT".equalsIgnoreCase(itemDto.getAcquisitionType()) || "RENTAL".equalsIgnoreCase(itemDto.getAcquisitionType())) {
                if (!product.isRentable() || product.getRentPerDay() == null || itemDto.getRentalPeriodDays() <= 0) {
                    throw new IllegalArgumentException("Product '" + product.getName() + "' is not rentable or rental period is invalid.");
                }
                detail.setTranType("RENT");
                detail.setRentNoOfDays(itemDto.getRentalPeriodDays());
                itemPrice = product.getRentPerDay().multiply(new BigDecimal(itemDto.getRentalPeriodDays()));
            } else {
                throw new IllegalArgumentException("Invalid acquisition type: " + itemDto.getAcquisitionType());
            }

            detail.setSellPrice(itemPrice);
            detail.setRoyaltyAmount(BigDecimal.ZERO); 
            
            totalAmount = totalAmount.add(itemPrice);
            invoiceDetails.add(detail);
        }

        invoice.setAmount(totalAmount);
        invoice.setInvoiceDetails(invoiceDetails);
        
        Invoice savedInvoice = invoiceRepository.save(invoice);

        for (InvoiceDetail detail : savedInvoice.getInvoiceDetails()) {
            UserLibrary libraryEntry = new UserLibrary();
            libraryEntry.setCustomerId(savedInvoice.getCustomerId());
            libraryEntry.setProductId(detail.getProductId());
            libraryEntry.setInvoiceDetailId(detail.getInvDtlId());
            libraryEntry.setStatus("ACTIVE");
            
            libraryEntry.setAcquisitionType(
                "SALE".equalsIgnoreCase(detail.getTranType()) ? "PURCHASE" : "RENTAL"
            );

            UserLibrary savedLibraryEntry = userLibraryRepository.save(libraryEntry);

            if ("RENTAL".equalsIgnoreCase(libraryEntry.getAcquisitionType())) {
                RentalLedger ledger = new RentalLedger();
                ledger.setUserLibrary(savedLibraryEntry);
                ledger.setRentStartDate(new Date());
                
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, detail.getRentNoOfDays());
                ledger.setRentEndDate(cal.getTime());
                
                rentalLedgerRepository.save(ledger);
            }
        }
        
        return mapToOrderResponseDTO(savedInvoice);
    }

    private OrderResponseDTO mapToOrderResponseDTO(Invoice invoice) {
        // ... your existing mapping logic ...
        OrderResponseDTO response = new OrderResponseDTO();
        response.setInvoiceId(invoice.getInvoiceId());
        response.setCustomerId(invoice.getCustomerId());
        response.setOrderDate(invoice.getDate());
        response.setTotalAmount(invoice.getAmount().doubleValue());
        response.setStatus("COMPLETED");

        List<OrderItemResponseDTO> itemResponses = invoice.getInvoiceDetails().stream().map(detail -> {
            OrderItemResponseDTO itemDto = new OrderItemResponseDTO();
            itemDto.setProductId(detail.getProductId());
            itemDto.setPrice(detail.getSellPrice().doubleValue());
            itemDto.setAcquisitionType("SALE".equalsIgnoreCase(detail.getTranType()) ? "PURCHASE" : "RENTAL");
            
            if (itemDto.getAcquisitionType().equals("RENTAL")) {
                rentalLedgerRepository.findById(detail.getInvDtlId())
                    .ifPresent(ledger -> itemDto.setRentalEndDate(ledger.getRentEndDate()));
            }
            return itemDto;
        }).collect(Collectors.toList());

        response.setItems(itemResponses);
        return response;
    }
}
