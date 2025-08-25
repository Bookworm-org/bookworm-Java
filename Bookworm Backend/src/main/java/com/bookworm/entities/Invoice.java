package com.bookworm.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoice_master") // Matches your table name
@Getter
@Setter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "cart_id") // As per your schema
    private Integer cartId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    // This establishes the link where Invoice is the parent
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = new Date();
        }
    }
}