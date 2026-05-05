package com.secon.UrlShortener.infrastructure.out.persistence;

import com.secon.UrlShortener.application.utilities.ToDomain;
import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.out.UserRepository;
import com.secon.UrlShortener.infrastructure.out.persistence.entities.JpaUserEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.jpa.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        JpaUserEntity userEntity = jpaUserRepository.save(new JpaUserEntity(user));
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
}
