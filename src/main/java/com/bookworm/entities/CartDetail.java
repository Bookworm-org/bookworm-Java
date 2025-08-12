package com.bookworm.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/*
 * Represents a single line item within a shopping cart (Cart).
 * Links a product to a cart and specifies rental info and cost.
 * Maps to the 'cart_details' table.
 */
@Entity
@Table(name = "cart_details")
@Getter
@Setter
public class CartDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_details_id")
    private Integer id;

    /*
     * The cart that this detail item belongs to. This is the "many" side
     * of the one-to-many relationship with Cart.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    /*
     * The product that this cart item represents.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /*
     * A flag to indicate if this item is a rental (true) or a purchase (false).
     */
    @Column(name = "is_rented", nullable = false)
    private boolean isRented;

    /*
     * The number of days for the rental. This will be null for purchases.
     */
    @Column(name = "rent_no_of_days")
    private Integer rentNumberOfDays;

    /*
     * The final cost for this specific line item after any offers or rental
     * calculations have been applied.
     */
    @Column(name = "offer_cost", precision = 10, scale = 2, nullable = false)
    private BigDecimal offerCost;

    /*
     * A flag that can be used to track if the item's price or details have
     * changed since it was added to the cart.
     */
    @Column(name = "is_updated")
    private boolean isUpdated;
}
