package com.secon.UrlShortener.application.service;

import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.model.enums.UserType;
import com.secon.UrlShortener.domain.out.UserRepository;
import com.secon.UrlShortener.domain.usecase.AuthProvider;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

public class AuthProviderImpl implements AuthProvider {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public AuthProviderImpl(UserRepository userRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
    }

    @Override
    public User register(String email, String password) {
        if(userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("An account with this email already exists!");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(
                email,
                encodedPassword,
                UserType.CLIENT,
                new ArrayList<>()
        );

        return userRepository.save(user);
    }

    @Override
    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }
}
