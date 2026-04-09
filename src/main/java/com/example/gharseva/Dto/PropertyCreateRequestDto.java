package com.example.gharseva.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PropertyCreateRequestDto {

    @NotNull
    private Boolean availabilityStatus;

    @NotBlank
    private String title;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String description;

    @NotNull
    @Min(0)
    private Integer pricePerMonth;

    @Size(max = 30)
    private List<@NotBlank @Size(max = 1000) String> imageUrls;

    @NotNull
    private Long ownerId;
}

