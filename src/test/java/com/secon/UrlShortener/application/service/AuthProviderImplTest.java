package com.secon.UrlShortener.application.service;

import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.model.enums.UserType;
import com.secon.UrlShortener.domain.out.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

public class AuthProviderImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthProviderImpl authProvider;

    User user;

    @BeforeEach
    public void setup(){
        user = new User(
                "user@email.com",
                "encodedPassword",
                UserType.CLIENT,
                new ArrayList<>()
        );
    }

    @Test
    public void shouldRegisterUser(){
        Mockito.<Optional<User>>when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.empty());

        User hardcodedUser = authProvider.register("user@email.com", "Senha#123");

        assertNotNull(hardcodedUser);
        assertEquals(user.getEmail(), hardcodedUser.getEmail());
        assertEquals(UserType.CLIENT, hardcodedUser.getUserType());
        assertNull(hardcodedUser.getUrls());
    }

    @Test
    public void shouldLoginUser(){
        Mockito.<Optional<User>>when(userRepository.findByEmail("user@email.com")).thenReturn(Optional.of(user));

        Optional<User> hardcodedUser = authProvider.login("user@email.com", "Senha#123");

        assertNotNull(hardcodedUser.get());
        assertEquals(user.getEmail(), hardcodedUser.get().getEmail());
        assertEquals(UserType.CLIENT, hardcodedUser.get().getUserType());
    }
}
