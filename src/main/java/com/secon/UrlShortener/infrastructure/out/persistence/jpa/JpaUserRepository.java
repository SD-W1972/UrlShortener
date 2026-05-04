package com.secon.UrlShortener.infrastructure.out.persistence.jpa;

import com.secon.UrlShortener.infrastructure.out.persistence.entities.JpaUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<JpaUserEntity, Long> {
    JpaUserEntity save(JpaUserEntity jpaUserEntity);
    List<JpaUserEntity> findAll();

    @Override
    Optional<JpaUserEntity> findById(Long id);

    void deleteById(Long id);
}
