package com.example.gharseva.Controller;

import com.example.gharseva.Dto.PropertyCreateRequestDto;
import com.example.gharseva.Dto.PropertyResponseDto;
import com.example.gharseva.Dto.PropertyUpdateRequestDto;
import com.example.gharseva.Service.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Properties", description = "Public property browsing + admin property management.")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/properties")
    @Operation(
            summary = "List properties",
            description = "Returns a pageable list of properties. Supports filtering by city, price range, availability, and ownerId."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of properties returned",
                    content = @Content(schema = @Schema(implementation = PropertyResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid query parameters", content = @Content)
    })
    public ResponseEntity<Page<PropertyResponseDto>> listProperties(
            @Parameter(description = "Filter by city (case-insensitive)") @RequestParam(required = false) String city,
            @Parameter(description = "Minimum monthly price (inclusive)") @RequestParam(required = false) Integer minPrice,
            @Parameter(description = "Maximum monthly price (inclusive)") @RequestParam(required = false) Integer maxPrice,
            @Parameter(description = "Filter by availability") @RequestParam(required = false) Boolean available,
            @Parameter(description = "Filter by owner user id") @RequestParam(required = false) Long ownerId,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(
                propertyService.searchProperties(city, minPrice, maxPrice, available, ownerId, pageable)
        );
    }

    @GetMapping("/properties/{id}")
    @Operation(summary = "Get property by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Property found"),
            @ApiResponse(responseCode = "404", description = "Property not found", content = @Content)
    })
    public ResponseEntity<PropertyResponseDto> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @PostMapping("/admin/properties")
    @Operation(summary = "Create property (admin)", description = "Creates a new property for the given ownerId.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Property created"),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "404", description = "Owner user not found", content = @Content)
    })
    public ResponseEntity<PropertyResponseDto> createProperty(@Valid @RequestBody PropertyCreateRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.createProperty(request));
    }

    @PutMapping("/admin/properties/{id}")
    @Operation(summary = "Update property (admin)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Property updated"),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "404", description = "Property/Owner not found", content = @Content)
    })
    public ResponseEntity<PropertyResponseDto> updateProperty(
            @PathVariable Long id,
            @Valid @RequestBody PropertyUpdateRequestDto request
    ) {
        return ResponseEntity.ok(propertyService.updateProperty(id, request));
    }

    @DeleteMapping("/admin/properties/{id}")
    @Operation(summary = "Delete property (admin)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Property deleted"),
            @ApiResponse(responseCode = "404", description = "Property not found", content = @Content)
    })
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}

