package com.secon.UrlShortener.application.port.out;

import com.secon.UrlShortener.domain.model.Url;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UrlRepository {
    Url save(Url url);
    Optional<Url> findByOriginalUrl(String url);
    List<Url> findAll();
    Optional<Url> findById(UUID id);
    void deleteById(UUID id);
    Optional<Url> findBySlug(String slug);
}
