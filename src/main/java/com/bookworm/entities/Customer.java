package com.bookworm.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

/*
 * Represents a registered customer of the service.
 * Contains personal and contact information, and links to the customer's
 * transactional history.
 * Maps to the 'customers' table.
 */
@Entity
@Getter
@Setter
@Table(name = "customer_master")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    @Column(name = "phone", length = 15, unique = true)
    private String phone;

    @Column(name = "dob")
    private LocalDate dateOfBirth;

    @Column(name = "age")
    private Integer age;

    @Column(name = "address", length = 255)
    private String address;

    @OneToMany(mappedBy = "customer")
    private Set<Cart> carts;

    //    @OneToMany(mappedBy = "customer")
    //    private Set<Invoice> invoices;
    //
    //    @OneToMany(mappedBy = "customer")
    //    private Set<RentDetail> rentDetails;
    //
    //    //@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    //    private Shelf shelf;
}
