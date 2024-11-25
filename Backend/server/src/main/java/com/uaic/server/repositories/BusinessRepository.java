package com.uaic.server.repositories;
import com.uaic.server.entities.Business;

import com.uaic.server.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    // Find by email (useful if emails are unique)
    Optional<Business> findByName(String name);

    // Find by phone number
    Optional<Business> findByAddress(String address);
    Optional<Business> findByIndustry(String industry);
}
