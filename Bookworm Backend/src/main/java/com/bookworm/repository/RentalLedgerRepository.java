package com.bookworm.repository;

import com.bookworm.entities.RentalLedger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalLedgerRepository extends JpaRepository<RentalLedger, Integer> {
}
