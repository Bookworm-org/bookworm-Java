// In file: src/main/java/com/bookworm/controller/AuthController.java

package com.bookworm.controller;

import com.bookworm.dto.request.LoginRequest;
import com.bookworm.dto.request.RegisterRequest;
import com.bookworm.dto.response.JwtResponse;
import com.bookworm.entities.Customer;
import com.bookworm.entities.Role;
import com.bookworm.repository.CustomerRepository;
import com.bookworm.repository.RoleRepository;
import com.bookworm.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Step 1: Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        // Step 2: Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Step 3: Get the UserDetails principal
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Step 4: Fetch the full Customer object to get ID and Name
        Customer customer = customerRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: Customer not found after authentication."));

        // Step 5: Generate the JWT token
        final String jwt = jwtUtil.generateToken(userDetails);

        // Step 6: Create the response with token, ID, and name
        return ResponseEntity.ok(new JwtResponse(jwt, customer.getId(), customer.getName()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (customerRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        Customer customer = new Customer();
        customer.setName(registerRequest.getName());
        customer.setEmail(registerRequest.getEmail());
        customer.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        customer.setRoles(roles);

        customerRepository.save(customer);
        return ResponseEntity.ok("User registered successfully!");
    }
}