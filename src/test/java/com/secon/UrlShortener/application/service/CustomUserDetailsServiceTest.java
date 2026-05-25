package com.secon.UrlShortener.application.service;

import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.model.enums.UserType;
import com.secon.UrlShortener.domain.out.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
                "user@email.com",
                "encodedPassword",
                UserType.ADMIN,
                new ArrayList<>()
        );
    }

    @Test
    void shouldLoadUserByUsernameWhenUserExists() {
        when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("user@email.com");

        assertNotNull(userDetails);
        assertEquals("user@email.com", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByEmail("notfound@email.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                customUserDetailsService.loadUserByUsername("notfound@email.com"));
    }

    @Test
    void shouldReturnClientRoleWhenUserTypeIsClient() {
        User clientUser = new User(
                "client@email.com",
                "encodedPassword",
                UserType.CLIENT,
                new ArrayList<>()
        );
        when(userRepository.findByEmail("client@email.com")).thenReturn(Optional.of(clientUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("client@email.com");

        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_CLIENT")));
    }

    @Test
    void shouldReturnAdminRoleWhenUserTypeIsAdmin() {
        User adminUser = new User(
                "admin@email.com",
                "encodedPassword",
                UserType.ADMIN,
                new ArrayList<>()
        );
        when(userRepository.findByEmail("admin@email.com")).thenReturn(Optional.of(adminUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("admin@email.com");

        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }
}