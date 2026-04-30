package com.secon.UrlShortener.domain.usecase;

import com.secon.UrlShortener.domain.model.Url;

public interface GetOriginalUrlUseCase {
    String originalUrl(String slug, String userAgent, String ipAddress);
}
