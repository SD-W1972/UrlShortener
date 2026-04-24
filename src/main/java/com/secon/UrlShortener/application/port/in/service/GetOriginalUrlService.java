package com.secon.UrlShortener.application.port.in.service;

import com.secon.UrlShortener.application.port.out.UrlRepository;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.model.usecase.GetOriginalUrlUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

public class GetOriginalUrlService implements GetOriginalUrlUseCase {

    @Autowired
    private UrlRepository urlRepository;

    @Cacheable(value = "originalUrl", key = "#slug")
    @Override
    public String originalUrl(String slug) {
        return urlRepository.findBySlug(slug).map(Url::getOriginalUrl).orElseThrow(() -> new IllegalArgumentException("URL not found"));
    }
}
