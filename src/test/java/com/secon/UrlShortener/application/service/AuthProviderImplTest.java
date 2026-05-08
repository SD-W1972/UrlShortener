package com.secon.UrlShortener.application.service;

import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.model.enums.UserType;
import com.secon.UrlShortener.domain.out.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension .class)
public class AuthProviderImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthProviderImpl authProvider;

    private PasswordEncoder passwordEncoder;
    private User user;

    @BeforeEach
    public void setup(){
        passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("Senha#123");

        user = new User(
                "user@email.com",
                encodedPassword,
                UserType.CLIENT,
                new ArrayList<>()
        );
    }

    @Test
    public void shouldRegisterUser(){
        Mockito.<Optional<User>>when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.empty());

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
                    User u = invocation.getArgument(0);
                    return u;
                });

        User hardcodedUser = authProvider.register("user@email.com", "Senha#123");

        assertNotNull(hardcodedUser);
        assertEquals(user.getEmail(), hardcodedUser.getEmail());
        assertEquals(UserType.CLIENT, hardcodedUser.getUserType());
        assertTrue(passwordEncoder.matches("Senha#123", hardcodedUser.getPassword()));
    }

    @Test
    public void shouldLoginUser(){
        Mockito.<Optional<User>>when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));

        Optional<User> hardcodedUser = authProvider.login("user@email.com", "Senha#123");

        assertTrue(hardcodedUser.isPresent());
        assertEquals(user.getEmail(), hardcodedUser.get().getEmail());
        assertEquals(UserType.CLIENT, hardcodedUser.get().getUserType());
    }

    @Test
    public void shouldNotLoginBecauseEmailDoesntExist(){
        Mockito.<Optional<User>>when(userRepository.findByEmail("nonexisting@email.com")).thenReturn(Optional.empty());

        Optional<User> result = authProvider.login("nonexisting@email.com", "Senha#123#");

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldNotLoginBecausePasswordDoesNotMatch(){
        Mockito.<Optional<User>>when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));

        Optional<User> hardcodedUser = authProvider.login("user@email.com", "WrongPassword");

        assertFalse(hardcodedUser.isPresent());
    }

    @Test
    public void shouldThrowExceptionWhenAccountAlreadyExistsOnRegister(){
        Mockito.<Optional<User>>when(userRepository.findByEmail("existing@email.com")).thenReturn(Optional.of(user));
        assertThrows(IllegalArgumentException.class, () ->
                authProvider.register("existing@email.com", "Senha#123#"));
    }
}
