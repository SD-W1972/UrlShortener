package com.secon.UrlShortener.infrastructure.out.persistence.repository;

import com.secon.UrlShortener.application.utilities.ToDomain;
import com.secon.UrlShortener.domain.out.UrlRepository;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.infrastructure.out.persistence.entity.jpa.JpaUrlEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.repository.jpa.JpaUrlRepository;

import java.util.List;
import java.util.Optional;

public class UrlRepositoryImpl implements UrlRepository {

    private final JpaUrlRepository jpaUrlRepository;

    public UrlRepositoryImpl(JpaUrlRepository jpaUrlRepository) {
        this.jpaUrlRepository = jpaUrlRepository;
    }

    @Override
    public Url save(Url url) {
        return ToDomain.toDomainUrl(jpaUrlRepository.save(new JpaUrlEntity(url)));
    }

    @Override
    public Optional<Url> findByOriginalUrl(String url) {
        return jpaUrlRepository.findByOriginalUrlHash(url)
                .map(ToDomain::toDomainUrl);
    }

    @Override
    public List<Url> findAll() {
        return jpaUrlRepository.findAll().stream()
                .map(ToDomain::toDomainUrl)
                .toList();
    }

    @Override
    public Optional<Url> findById(Long id) {
        return jpaUrlRepository.findById(id)
                .map(ToDomain::toDomainUrl);
    }

    @Override
    public void deleteById(Long id) {
        jpaUrlRepository.deleteById(id);
    }

    @Override
    public Optional<Url> findBySlug(String slug) {
        return jpaUrlRepository.findBySlug(slug)
                .map(ToDomain::toDomainUrl);
    }
}