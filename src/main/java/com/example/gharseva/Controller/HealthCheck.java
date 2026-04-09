package com.example.gharseva.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
@Tag(name = "Health")
public class HealthCheck {
    @GetMapping
    @Operation(summary = "Health check")
    public String ping() {
        return "Ok";
    }
}
