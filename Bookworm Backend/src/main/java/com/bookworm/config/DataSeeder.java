package com.bookworm.config;

import com.bookworm.entities.Customer;
import com.bookworm.entities.Role;
import com.bookworm.repository.CustomerRepository;
import com.bookworm.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        if (customerRepository.findByEmail("admin@bookworm.com").isEmpty()) {
            Customer admin = new Customer();
            admin.setName("Admin User");
            admin.setEmail("admin@bookworm.com");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));
            admin.setRoles(Set.of(adminRole));
            customerRepository.save(admin);
        }
    }
}