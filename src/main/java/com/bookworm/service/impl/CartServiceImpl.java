package com.bookworm.service.impl;

import com.bookworm.dto.request.CartItemRequestDTO;
import com.bookworm.dto.response.CartItemResponseDTO;
import com.bookworm.dto.response.CartResponseDTO;
import com.bookworm.entities.Cart;
import com.bookworm.entities.CartDetail;
import com.bookworm.entities.Customer;
import com.bookworm.entities.Product;
import com.bookworm.repository.CartDetailRepository;
import com.bookworm.repository.CartRepository;
import com.bookworm.repository.CustomerRepository;
import com.bookworm.repository.ProductRepository;
import com.bookworm.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Override
    public CartResponseDTO addProductToCart(Integer customerId, CartItemRequestDTO requestDTO) {
        Cart cart = getOrCreateActiveCart(customerId);
        Product product = productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + requestDTO.getProductId()));

        CartDetail cartDetail = cart.getCartDetails().stream()
                .filter(cd -> cd.getProduct().getId().equals(requestDTO.getProductId()))
                .findFirst()
                .orElse(new CartDetail());

        cartDetail.setCart(cart);
        cartDetail.setProduct(product);
        cartDetail.setRented(requestDTO.isRented());

        if (requestDTO.isRented()) {
            cartDetail.setRentNumberOfDays(requestDTO.getRentNumberOfDays());
            cartDetail.setOfferCost(product.getRentPerDay().multiply(BigDecimal.valueOf(requestDTO.getRentNumberOfDays())));
        } else {
            cartDetail.setRentNumberOfDays(null);
            cartDetail.setOfferCost(product.getOfferPrice());
        }
        
        if (cartDetail.getId() == null) {
            cart.getCartDetails().add(cartDetail);
        }

        recalculateCartTotal(cart);
        Cart savedCart = cartRepository.save(cart);
        return toCartResponseDTO(savedCart); // Using manual mapper
    }

    @Override
    public CartResponseDTO removeProductFromCart(Integer customerId, Integer productId) {
        Cart cart = getActiveCart(customerId);
        CartDetail cartDetail = cartDetailRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found in cart"));

        cart.getCartDetails().remove(cartDetail);
        cartDetailRepository.delete(cartDetail);

        recalculateCartTotal(cart);
        Cart savedCart = cartRepository.save(cart);
        return toCartResponseDTO(savedCart); // Using manual mapper
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponseDTO getCartByCustomerId(Integer customerId) {
        Cart cart = getActiveCart(customerId);
        return toCartResponseDTO(cart); // Using manual mapper
    }

    private Cart getActiveCart(Integer customerId) {
        return cartRepository.findByCustomerIdAndIsActiveTrue(customerId)
                .orElseThrow(() -> new NoSuchElementException("No active cart found for customer id: " + customerId));
    }

    private Cart getOrCreateActiveCart(Integer customerId) {
        return cartRepository.findByCustomerIdAndIsActiveTrue(customerId)
                .orElseGet(() -> {
                    Customer customer = customerRepository.findById(customerId)
                            .orElseThrow(() -> new NoSuchElementException("Customer not found with id: " + customerId));
                    Cart newCart = new Cart();
                    newCart.setCustomer(customer);
                    newCart.setActive(true);
                    newCart.setCost(BigDecimal.ZERO);
                    newCart.setCartDetails(new HashSet<>()); // Corrected to HashSet
                    return cartRepository.save(newCart);
                });
    }

    private void recalculateCartTotal(Cart cart) {
        BigDecimal totalCost = cart.getCartDetails().stream()
                .map(CartDetail::getOfferCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setCost(totalCost);
    }
    
    // --- MANUAL MAPPING METHODS ---

    /**
     * Manually converts a Cart entity to a CartResponseDTO.
     */
    private CartResponseDTO toCartResponseDTO(Cart cart) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setCartId(cart.getId());
        dto.setCustomerId(cart.getCustomer().getId());
        dto.setTotalCost(cart.getCost());
        dto.setTotalItems(cart.getCartDetails().size()); // Set total items
        
        List<CartItemResponseDTO> itemDTOs = cart.getCartDetails().stream()
                .map(this::toCartItemResponseDTO)
                .collect(Collectors.toList());
        dto.setItems(itemDTOs);
        
        return dto;
    }

    /**
     * Manually converts a CartDetail entity to a CartItemResponseDTO.
     */
    private CartItemResponseDTO toCartItemResponseDTO(CartDetail cartDetail) {
        CartItemResponseDTO itemDto = new CartItemResponseDTO();
        itemDto.setProductId(cartDetail.getProduct().getId());
        itemDto.setProductName(cartDetail.getProduct().getName());
        itemDto.setAuthor(cartDetail.getProduct().getAuthor());
        itemDto.setImageSource(cartDetail.getProduct().getImageSource());
        itemDto.setShortDescription(cartDetail.getProduct().getShortDescription()); // Set short description
        itemDto.setItemCost(cartDetail.getOfferCost());
        itemDto.setRented(cartDetail.isRented());
        itemDto.setRentNumberOfDays(cartDetail.getRentNumberOfDays());
        itemDto.setQuantity(1); // Assuming quantity is always 1 per line item
        
        return itemDto;
    }
}