package com.secon.UrlShortener.domain.usecase;

import com.secon.UrlShortener.domain.model.User;

import java.util.Optional;

public interface AuthProvider {
    User register(String email, String password);
    Optional<User> login(String email, String password);
}
