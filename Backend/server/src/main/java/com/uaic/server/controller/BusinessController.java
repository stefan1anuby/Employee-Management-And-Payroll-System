package com.uaic.server.controller;

import com.uaic.server.entities.Business;
import com.uaic.server.services.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/businesses")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @GetMapping
    public ResponseEntity<Iterable<Business>> getBusinesses() {
        Iterable<Business> businesses = businessService.findBusinesses();
        return new ResponseEntity<>(businesses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Business> getBusiness(@PathVariable Long id) {
        Optional<Business> business = businessService.findBusinessById(id);
        if (business.isPresent()) {
            return new ResponseEntity<>(business.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> addBusiness(@RequestBody Business business) {
        businessService.createBusiness(business);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBusiness(@PathVariable Long id, @RequestBody Business business) {
        if (businessService.checkExistentBusinessById(id)) {
            business.setId(id);
            businessService.createBusiness(business);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id) {
        if (businessService.checkExistentBusinessById(id)) {
            businessService.deleteBusinessById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
