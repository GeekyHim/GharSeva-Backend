package com.example.gharseva.Service;

import com.example.gharseva.Dto.PropertyCreateRequestDto;
import com.example.gharseva.Dto.PropertyResponseDto;
import com.example.gharseva.Dto.PropertyUpdateRequestDto;
import com.example.gharseva.Entity.UserEntity;
import com.example.gharseva.Exception.ResourceNotFoundException;
import com.example.gharseva.Entity.PropertyEntity;
import com.example.gharseva.Repository.PropertyRepository;
import com.example.gharseva.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public PropertyService(PropertyRepository propertyRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<PropertyResponseDto> searchProperties(
            String city,
            Integer minPrice,
            Integer maxPrice,
            Boolean availabilityStatus,
            Long ownerId,
            Pageable pageable
    ) {
        return propertyRepository.searchProperties(city, minPrice, maxPrice, availabilityStatus, ownerId, pageable)
                .map(this::toResponseDto);
    }

    @Transactional(readOnly = true)
    public PropertyResponseDto getPropertyById(Long id) {
        PropertyEntity entity = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with ID: " + id));
        return toResponseDto(entity);
    }

    @Transactional
    public PropertyResponseDto createProperty(PropertyCreateRequestDto request) {
        UserEntity owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner user not found with ID: " + request.getOwnerId()));

        PropertyEntity entity = PropertyEntity.builder()
                .availabilityStatus(Boolean.TRUE.equals(request.getAvailabilityStatus()))
                .title(request.getTitle())
                .address(request.getAddress())
                .city(request.getCity())
                .description(request.getDescription())
                .pricePerMonth(request.getPricePerMonth())
                .imageUrls(request.getImageUrls() == null ? new ArrayList<>() : new ArrayList<>(request.getImageUrls()))
                .owner(owner)
                .build();

        return toResponseDto(propertyRepository.save(entity));
    }

    @Transactional
    public PropertyResponseDto updateProperty(Long id, PropertyUpdateRequestDto request) {
        PropertyEntity entity = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with ID: " + id));

        if (request.getAvailabilityStatus() != null) {
            entity.setAvailabilityStatus(request.getAvailabilityStatus());
        }
        if (request.getTitle() != null) {
            entity.setTitle(request.getTitle());
        }
        if (request.getAddress() != null) {
            entity.setAddress(request.getAddress());
        }
        if (request.getCity() != null) {
            entity.setCity(request.getCity());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.getPricePerMonth() != null) {
            entity.setPricePerMonth(request.getPricePerMonth());
        }
        if (request.getImageUrls() != null) {
            entity.setImageUrls(new ArrayList<>(request.getImageUrls()));
        }
        if (request.getOwnerId() != null) {
            UserEntity owner = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Owner user not found with ID: " + request.getOwnerId()));
            entity.setOwner(owner);
        }

        return toResponseDto(propertyRepository.save(entity));
    }

    @Transactional
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Property not found with ID: " + id);
        }
        propertyRepository.deleteById(id);
    }

    private PropertyResponseDto toResponseDto(PropertyEntity entity) {
        return PropertyResponseDto.builder()
                .id(entity.getId())
                .availabilityStatus(entity.isAvailabilityStatus())
                .title(entity.getTitle())
                .address(entity.getAddress())
                .city(entity.getCity())
                .description(entity.getDescription())
                .pricePerMonth(entity.getPricePerMonth())
                .imageUrls(entity.getImageUrls())
                .ownerId(entity.getOwner() == null ? null : entity.getOwner().getId())
                .build();
    }
}
