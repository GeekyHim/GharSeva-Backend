package com.example.gharseva.Dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PropertyResponseDto {
    Long id;
    boolean availabilityStatus;
    String title;
    String address;
    String city;
    String description;
    Integer pricePerMonth;
    List<String> imageUrls;
    Long ownerId;
}

