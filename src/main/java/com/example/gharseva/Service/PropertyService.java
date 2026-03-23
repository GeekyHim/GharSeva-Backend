package com.example.gharseva.Service;

import com.example.gharseva.Entity.PropertyEntity;
import com.example.gharseva.Repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Transactional(readOnly = true)
    public List<PropertyEntity> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PropertyEntity getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));
    }

    public PropertyEntity saveProperty(PropertyEntity property) {
        return propertyRepository.save(property);
    }
}
