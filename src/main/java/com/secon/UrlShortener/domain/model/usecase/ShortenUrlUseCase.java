package com.secon.UrlShortener.domain.model.usecase;

import java.util.UUID;

public interface ShortenUrlUseCase {
    String encodeToSlug(String originalUrl);
}
