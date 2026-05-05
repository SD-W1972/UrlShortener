package com.secon.UrlShortener.infrastructure.out.persistence;

import com.secon.UrlShortener.application.utilities.ToDomain;
import com.secon.UrlShortener.domain.out.ClickRepository;
import com.secon.UrlShortener.domain.model.Click;
import com.secon.UrlShortener.infrastructure.out.persistence.entities.JpaClickEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.jpa.JpaClickRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClickRepositoryImpl implements ClickRepository {

    private final JpaClickRepository jpaClickRepository;

    public ClickRepositoryImpl(JpaClickRepository jpaClickRepository) {
        this.jpaClickRepository = jpaClickRepository;
    }

    @Override
    public Click save(Click click) {
        JpaClickEntity clickEntity = jpaClickRepository.save(new JpaClickEntity(click));
        return ToDomain.toDomainClick(clickEntity);
    }

    @Override
    public List<Click> findAll() {
        List<JpaClickEntity> clickEntities = jpaClickRepository.findAll();
        return clickEntities.stream()
                .map(ToDomain::toDomainClick)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Click> findById(Long id) {
        return jpaClickRepository.findById(id)
                .map(ToDomain::toDomainClick);
    }

    @Override
    public void deleteById(Long id) {
        jpaClickRepository.deleteById(id);
    }
}