package com.secon.UrlShortener.domain.model.DTOS;

public record RegisterRequestDTO(
        String email,
        String password
) {}
