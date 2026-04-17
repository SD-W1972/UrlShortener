package com.secon.UrlShortener.infrastructure.adapter.out.persistence;

import com.secon.UrlShortener.infrastructure.adapter.out.persistence.entities.JpaUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUrlRepository extends JpaRepository<JpaUrlEntity, UUID> {
    @Override
    Optional<JpaUrlEntity> findById(UUID uuid);

    Optional<JpaUrlEntity> findBySlug(String slug);

    Optional<List<JpaUrlEntity>> findALl();

    @Query("SELECT u FROM JpaUrlEntity u WHERE u.originalUrlHash = :hash")
    Optional<JpaUrlEntity> findByOriginalUrlHash(@Param("hash") String hash);

    boolean existsBySlug(String slug);

    void deleteBySlug(String slug);
}
