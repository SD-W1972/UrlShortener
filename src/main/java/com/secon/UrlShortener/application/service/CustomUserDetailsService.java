package com.secon.UrlShortener.application.service;

import com.secon.UrlShortener.domain.out.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("🔍 Attempting to load user: {}", email);
        return userRepository.findByEmail(email)
                .map(user -> {
                    logger.info("✅ User found: {} with role: {}", email, user.getUserType().name());
                    return org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getUserType().name())
                        .build();
                })
                .orElseThrow(() -> {
                    logger.warn("❌ User not found: {}", email);
                    return new UsernameNotFoundException("User not found: " + email);
                });
    }
}

