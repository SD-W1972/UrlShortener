package com.secon.UrlShortener.infrastructure.adapter.out.persistence;

import com.secon.UrlShortener.application.port.out.UrlRepository;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.infrastructure.adapter.out.persistence.entities.JpaUrlEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class UrlRepositoryImpl implements UrlRepository {

    private final JpaUrlRepository jpaUrlRepository;

    public UrlRepositoryImpl(JpaUrlRepository jpaUrlRepository) {
        this.jpaUrlRepository = jpaUrlRepository;
    }

    @Override
    public Url save(Url url) {
        JpaUrlEntity urlEntity = this.jpaUrlRepository.save(new JpaUrlEntity(url));
        return toDomain(urlEntity);
    }

    @Override
    public Url findByOriginalUrl(String url) {
        Optional<JpaUrlEntity> urlEntity = this.jpaUrlRepository.findByOriginalUrlHash(url);
        return urlEntity.map(entity -> new Url(
                entity.getId(),
                entity.getOriginalUrl(),
                entity.getSlug(),
                entity.getCreatedAt(),
                entity.getExpiresAt(),
                entity.isActive())
        ).orElse(null);
    }

    @Override
    public List<Url> findAll() {
        List<JpaUrlEntity> urlEntity = this.jpaUrlRepository.findAll();
        return urlEntity.stream().map(entity -> toDomain(entity)).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Url findById(UUID id) {
        Optional<JpaUrlEntity> jpaUrlEntity = jpaUrlRepository.findById(id);
        return (jpaUrlEntity.isPresent()) ? toDomain(jpaUrlEntity.get()) : null;
    }

    @Override
    public void deleteById(UUID id) {
        jpaUrlRepository.deleteById(id);
    }

    public Url toDomain(JpaUrlEntity entity){
        return new Url(
                entity.getId(),
                entity.getOriginalUrl(),
                entity.getSlug(),
                entity.getCreatedAt(),
                entity.getExpiresAt(),
                entity.isActive()
        );
    }
}
