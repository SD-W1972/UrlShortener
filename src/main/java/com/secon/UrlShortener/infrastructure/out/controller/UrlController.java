package com.secon.UrlShortener.infrastructure.out.controller;

import com.secon.UrlShortener.domain.usecase.GetOriginalUrlUseCase;
import com.secon.UrlShortener.domain.usecase.ShortenUrlUseCase;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {

    private final ShortenUrlUseCase shortenUrl;
    private final GetOriginalUrlUseCase getOriginalUrl;

    public UrlController(ShortenUrlUseCase shortenUrl, GetOriginalUrlUseCase getOriginalUrl) {
        this.shortenUrl = shortenUrl;
        this.getOriginalUrl = getOriginalUrl;
    }

    @PostMapping("/slug")
    public ResponseEntity<String> getSlug(@RequestBody String originalUrl){
        return ResponseEntity.status(201).body(shortenUrl.encodeToSlug(originalUrl));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String slug, HttpServletRequest request){
        String userAgent = request.getHeader("User-Agent");
        String ipAddress = request.getRemoteAddr();

        String originalUrl = getOriginalUrl.originalUrl(slug, userAgent, ipAddress);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", originalUrl)
                .build();
    }
}
