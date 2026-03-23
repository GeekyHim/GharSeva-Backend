package com.example.gharseva.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gharseva.Entity.PropertyEntity;

public interface PropertyRepository extends JpaRepository<PropertyEntity, Long>{

    // First - Basic Filters
    List<PropertyEntity> findByCityIgnoreCase(String city);

    List<PropertyEntity> findByPricePerMonthLessThanEqual(double price);

    List<PropertyEntity> findByAvailabilityStatus(boolean status);


    // Now i put - Combined Filter
    List<PropertyEntity> findByCityIgnoreCaseAndPricePerMonthLessThanEqual(
            String city, double price);



    // Owner Properties
    List<PropertyEntity> findByOwnerId(Long ownerId);

    // Pagination

}

