package com.uaic.server.repositories;
import com.uaic.server.entities.Business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessRepository extends JpaRepository<Business, UUID> {
    // Find by email (useful if emails are unique)
    Optional<Business> findByName(String name);

    // Find by phone number
    Optional<Business> findByAddress(String address);
    Optional<Business> findByIndustry(String industry);
}
