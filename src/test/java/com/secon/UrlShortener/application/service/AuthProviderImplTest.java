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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthProviderImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthProviderImpl authProvider;

    private User user;
    private String rawPassword = "Senha#123";
    private String encodedPassword = "$2a$10$abc123def456ghi789jkl";

    @BeforeEach
    public void setup() {
        user = new User(
                "user@email.com",
                encodedPassword,
                UserType.CLIENT,
                new ArrayList<>()
        );
    }

    @Test
    public void shouldRegisterUser() {
        when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User registeredUser = authProvider.register("user@email.com", rawPassword);

        assertNotNull(registeredUser);
        assertEquals("user@email.com", registeredUser.getEmail());
        assertEquals(UserType.CLIENT, registeredUser.getUserType());
        assertEquals(encodedPassword, registeredUser.getPassword());
    }

    @Test
    public void shouldLoginUser() {
        when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, user.getPassword())).thenReturn(true);

        Optional<User> result = authProvider.login("user@email.com", rawPassword);

        assertTrue(result.isPresent());
        assertEquals(user.getEmail(), result.get().getEmail());
        assertEquals(UserType.CLIENT, result.get().getUserType());
    }

    @Test
    public void shouldNotLoginBecauseEmailDoesntExist() {
        when(userRepository.findByEmail("nonexisting@email.com")).thenReturn(Optional.empty());

        Optional<User> result = authProvider.login("nonexisting@email.com", "Senha#123#");

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldNotLoginBecausePasswordDoesNotMatch() {
        when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("WrongPassword", user.getPassword())).thenReturn(false);

        Optional<User> result = authProvider.login("user@email.com", "WrongPassword");

        assertFalse(result.isPresent());
    }

    @Test
    public void shouldThrowExceptionWhenAccountAlreadyExistsOnRegister() {
        when(userRepository.findByEmail("existing@email.com")).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () ->
                authProvider.register("existing@email.com", "Senha#123#"));
    }
}