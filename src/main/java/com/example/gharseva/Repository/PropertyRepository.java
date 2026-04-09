package com.example.gharseva.Repository;

import com.example.gharseva.Entity.PropertyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

    @Query("""
            SELECT p
            FROM PropertyEntity p
            WHERE (:city IS NULL OR LOWER(p.city) = LOWER(:city))
              AND (:minPrice IS NULL OR p.pricePerMonth >= :minPrice)
              AND (:maxPrice IS NULL OR p.pricePerMonth <= :maxPrice)
              AND (:availabilityStatus IS NULL OR p.availabilityStatus = :availabilityStatus)
              AND (:ownerId IS NULL OR p.owner.id = :ownerId)
            """)
    Page<PropertyEntity> searchProperties(
            @Param("city") String city,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("availabilityStatus") Boolean availabilityStatus,
            @Param("ownerId") Long ownerId,
            Pageable pageable
    );
}

