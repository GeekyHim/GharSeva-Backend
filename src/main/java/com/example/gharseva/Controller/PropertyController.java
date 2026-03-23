package com.example.gharseva.Controller;

import com.example.gharseva.Entity.PropertyEntity;
import com.example.gharseva.Service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    // Public listing for Home page
    @GetMapping("/properties")
    public ResponseEntity<List<PropertyEntity>> listProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    // Get single property by ID
    @GetMapping("/properties/{id}")
    public ResponseEntity<PropertyEntity> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    // Admin add
    @PostMapping("/admin/properties")
    public ResponseEntity<PropertyEntity> createProperty(@Valid @RequestBody PropertyEntity property) {
        PropertyEntity saved = propertyService.saveProperty(property);
        return ResponseEntity.ok(saved);
    }
}

