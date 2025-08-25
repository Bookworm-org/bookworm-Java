package com.bookworm.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_details") // Matches your table name
@Getter
@Setter
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inv_dtl_id")
    private Integer invDtlId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "rent_no_of_days")
    private Integer rentNoOfDays; // Null for purchases

    @Column(name = "royalty_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal royaltyAmount;

    @Column(name = "sell_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal sellPrice;

    @Column(name = "tran_type", nullable = false, length = 20)
    private String tranType;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    // This is the owning side of the relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false) // Foreign Key
    private Invoice invoice;
}
