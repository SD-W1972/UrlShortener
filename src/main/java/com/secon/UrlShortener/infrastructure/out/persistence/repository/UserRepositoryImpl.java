package com.secon.UrlShortener.infrastructure.out.persistence.repository;

import com.secon.UrlShortener.application.utilities.ToDomain;
import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.out.UserRepository;
import com.secon.UrlShortener.infrastructure.out.persistence.entity.jpa.JpaUserEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.repository.jpa.JpaUserRepository;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        return ToDomain.toDomainUser(jpaUserRepository.save(new JpaUserEntity(user)));
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream()
                .map(ToDomain::toDomainUser)
                .toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(ToDomain::toDomainUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(ToDomain::toDomainUser);
    }

    @Override
    public void deleteById(Long id) {
        jpaUserRepository.deleteById(id);
    }
}