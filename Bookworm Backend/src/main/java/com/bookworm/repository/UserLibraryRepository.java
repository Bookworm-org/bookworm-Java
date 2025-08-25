package com.bookworm.repository;

import com.bookworm.entities.UserLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserLibraryRepository extends JpaRepository<UserLibrary, Integer> {

    /**
     * Finds all library entries for a given customer, filtered by acquisition type and status.
     * This is the primary method used to build the "MyShelf" and "MyLibrary" views.
     */
    List<UserLibrary> findByCustomerIdAndAcquisitionTypeAndStatus(
        Integer customerId, String acquisitionType, String status
    );
}