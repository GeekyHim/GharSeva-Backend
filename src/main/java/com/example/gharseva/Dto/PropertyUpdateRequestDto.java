package com.example.gharseva.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PropertyUpdateRequestDto {

    private Boolean availabilityStatus;

    private String title;

    private String address;

    private String city;

    private String description;

    @Min(0)
    private Integer pricePerMonth;

    @Size(max = 30)
    private List<@Size(max = 1000) String> imageUrls;

    private Long ownerId;
}

