package com.secon.UrlShortener.infrastructure.out.persistence.repository.jpa;

import com.secon.UrlShortener.infrastructure.out.persistence.entity.jpa.JpaUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaUrlRepository extends JpaRepository<JpaUrlEntity, Long> {
    @Override
    Optional<JpaUrlEntity> findById(Long id);

    Optional<JpaUrlEntity> findBySlug(String slug);

    Optional<List<JpaUrlEntity>> findALl();

    @Query("SELECT u FROM JpaUrlEntity u WHERE u.originalUrlHash = :hash")
    Optional<JpaUrlEntity> findByOriginalUrlHash(@Param("hash") String hash);

    boolean existsBySlug(String slug);

    void deleteBySlug(String slug);
}
