package com.bookworm.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;

import jakarta.persistence.GeneratedValue;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents an entry in the user_library table using Lombok.
 * Each record signifies a grant of access (entitlement) for a specific product
 * to a specific customer, detailing how it was acquired (e.g., purchase, rental).
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "userLibraryId") // Only use the ID for equals/hashCode
@Entity
@Table(name = "user_library")
public class UserLibrary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_library_id")
    private Integer userLibraryId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "invoice_detail_id", nullable = false, unique = true)
    private Integer invoiceDetailId;

    @Column(name = "acquisition_type", nullable = false, length = 20)
    private String acquisitionType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "acquisition_timestamp", nullable = false, updatable = false)
    private Date acquisitionTimestamp;

    @Column(name = "status", nullable = false, length = 20)
    private String status;
    
    @PrePersist
    protected void onCreate() {
        if (acquisitionTimestamp == null) {
            acquisitionTimestamp = new Date();
        }
    }
}