package com.secon.UrlShortener.domain.usecase;

import java.util.UUID;

public interface ShortenUrlUseCase {
    String encodeToSlug(String originalUrl);
}
