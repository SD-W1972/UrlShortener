package com.secon.UrlShortener.domain.out;

import com.secon.UrlShortener.domain.model.Url;

import java.util.List;
import java.util.Optional;

public interface UrlRepository {
    Url save(Url url);
    Optional<Url> findByOriginalUrl(String url);
    List<Url> findAll();
    Optional<Url> findById(Long id);
    void deleteById(Long id);
    Optional<Url> findBySlug(String slug);
}
