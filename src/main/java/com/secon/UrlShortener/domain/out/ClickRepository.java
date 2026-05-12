package com.secon.UrlShortener.domain.out;

import com.secon.UrlShortener.domain.model.Click;

import java.util.List;
import java.util.Optional;

public interface ClickRepository {
    Click save(Click click);
    List<Click> findAll();
    Optional<Click> findById(Long id);
    void deleteById(Long id);
    List<Click> findAllByOriginalUrl(String originalUrl);
}
