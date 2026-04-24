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
    public Optional<Url> findByOriginalUrl(String url) {
        return jpaUrlRepository.findByOriginalUrlHash(url)
                .map(this::toDomain);
    }

    @Override
    public List<Url> findAll() {
        List<JpaUrlEntity> urlEntity = this.jpaUrlRepository.findAll();
        return urlEntity.stream().map(entity -> toDomain(entity)).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Url> findById(Long id) {
        Optional<JpaUrlEntity> jpaUrlEntity = jpaUrlRepository.findById(id);
        return jpaUrlEntity.map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaUrlRepository.deleteById(id);
    }

    @Override
    public Optional<Url> findBySlug(String slug){
        Optional<JpaUrlEntity> jpaUrlEntity = jpaUrlRepository.findBySlug(slug);
        return jpaUrlEntity.map(this::toDomain);
    }

    public Url toDomain(JpaUrlEntity entity){
        return new Url(

                entity.getOriginalUrl(),
                entity.getSlug(),
                entity.getCreatedAt(),
                entity.getExpiresAt(),
                entity.isActive()
        );
    }
}
