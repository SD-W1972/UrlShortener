package com.secon.UrlShortener.infrastructure.adapter.out.persistence;

import com.secon.UrlShortener.application.port.out.UrlRepository;
import com.secon.UrlShortener.domain.model.Url;

import java.util.List;
import java.util.UUID;

public class UrlRepositoryImpl implements UrlRepository {

    private final JpaUrlRepository jpaUrlRepository;

    @Override
    public Url save(Url url) {
        return null;
    }

    @Override
    public Url findByOriginalUrl(String url) {
        return null;
    }

    @Override
    public List<Url> findAll() {
        return List.of();
    }

    @Override
    public Url findById(UUID id) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }
}
