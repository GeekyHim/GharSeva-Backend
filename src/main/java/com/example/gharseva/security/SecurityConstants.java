package com.example.gharseva.security;

public final class SecurityConstants {
    private SecurityConstants() {
    }

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    /**
     * Matches the OpenAPI security scheme name in {@code OpenApiConfig}.
     */
    public static final String OPENAPI_BEARER_SCHEME = "bearerAuth";
}

