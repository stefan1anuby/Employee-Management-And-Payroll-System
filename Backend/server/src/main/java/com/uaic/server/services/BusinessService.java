package com.uaic.server.services;

import com.uaic.server.entities.Business;
import com.uaic.server.entities.Employee;
import com.uaic.server.repositories.BusinessRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {

    @Autowired
    private BusinessRepository businessRepository;

    // Create a new business
    public Business createBusiness(Business business) {
        return businessRepository.save(business);
    }

    // Get all employees in a business
    public List<Employee> getEmployeesByBusiness(Long businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new EntityNotFoundException("Business not found"));
        return business.getEmployees();
    }

    public boolean checkExistentBusinessById(Long id) {
        return businessRepository.existsById(id);
    }


    public Optional<Business> findBusinessById(Long id) {
        return businessRepository.findById(id);
    }

    public Optional<Business> findBusinessByName(String name) {
        return businessRepository.findByName(name);
    }

    public Optional<Business> findBusinessByAddress(String address) {
        return businessRepository.findByAddress(address);
    }


    public Iterable<Business> findBusinesses() {
        return businessRepository.findAll();
    }

    public void deleteBusiness(Business business) {
        businessRepository.delete(business);
    }

    public void deleteBusinessById(Long id) {
        businessRepository.deleteById(id);
    }

    public void updateBusiness(Business business) {
        businessRepository.save(business);
    }


}
