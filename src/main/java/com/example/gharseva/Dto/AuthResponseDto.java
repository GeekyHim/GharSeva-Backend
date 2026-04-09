package com.example.gharseva.Dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthResponseDto {
    String message;
    String status;
    Long userId;
    String token;
}

