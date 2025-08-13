package com.bookworm.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*
 * Represents the rental_ledger table using Lombok. This entity stores metadata
 * specific only to rental transactions and has a one-to-one relationship
 * with a UserLibrary record.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "userLibrary") // Exclude the parent object to prevent circular logging
@EqualsAndHashCode(of = "userLibraryId") // Only use the ID for equals/hashCode
@Entity
@Table(name = "rental_ledger")
public class RentalLedger implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * The primary key of this table. It is also a foreign key that maps
     * directly to the user_library_id of the parent entitlement record.
     */
    @Id
    @Column(name = "user_library_id")
    private Integer userLibraryId;

    /*
     * The exact date and time the rental period begins.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rent_start_date", nullable = false)
    private Date rentStartDate;

    /**
     * The exact date and time the rental period expires.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rent_end_date", nullable = false)
    private Date rentEndDate;

    /*
     * An optional timestamp to track when a background process
     * last checked this rental's status for expiration.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_status_check")
    private Date lastStatusCheck;

    /*
     * Defines the one-to-one relationship back to the UserLibrary entity.
     * The @MapsId annotation indicates that the primary key of this entity
     * (RentalLedger) is populated from the associated UserLibrary entity.
     * This creates the shared primary key relationship.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_library_id")
    private UserLibrary userLibrary;
}
