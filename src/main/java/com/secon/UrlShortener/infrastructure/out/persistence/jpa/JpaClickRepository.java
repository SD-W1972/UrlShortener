package com.secon.UrlShortener.infrastructure.out.persistence.jpa;

import com.secon.UrlShortener.infrastructure.out.persistence.entities.JpaClickEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaClickRepository extends JpaRepository<JpaClickEntity, Long> {
    @Override
    Optional<JpaClickEntity> findById(Long id);
    @Override
    List<JpaClickEntity> findAll();

    void deleteById(Long id);

}
