package com.secon.UrlShortener.infrastructure.out.controller;

import com.secon.UrlShortener.domain.usecase.ShortenUrlUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UrlController {

    private final ShortenUrlUseCase shortenUrlUseCase;

    public UrlController(ShortenUrlUseCase shortenUrlUseCase) {
        this.shortenUrlUseCase = shortenUrlUseCase;
    }

    @PostMapping
    public ResponseEntity<String> getSlug(@RequestBody String originalUrl){
        return ResponseEntity.status(201).body(shortenUrlUseCase.encodeToSlug(originalUrl));
    }
}
