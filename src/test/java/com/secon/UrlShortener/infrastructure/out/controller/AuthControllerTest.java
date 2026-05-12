package com.secon.UrlShortener.infrastructure.out.controller;

import com.secon.UrlShortener.application.service.AuthProviderImpl;
import com.secon.UrlShortener.domain.model.DTOS.RegisterRequestDTO;
import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.model.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthProviderImpl authProvider;

    @InjectMocks
    private AuthController authController;

    private User user;
    private RegisterRequestDTO requestDTO;

    @BeforeEach
    public void setup(){
        user = new User("right@email.com", "rightPassword", UserType.CLIENT, new ArrayList<>());
        requestDTO = new RegisterRequestDTO(user.getEmail(), user.getPassword());
    }

    @Test
    public void shouldThrowExceptionWhenEmailAlreadyExists(){
        when(authProvider.register("existing@email.com", "passwordValid")).thenThrow(IllegalArgumentException.class);

        RegisterRequestDTO requestDTOAlreadyExistingEmail = new RegisterRequestDTO("existing@email.com", "passwordValid");

        assertEquals(HttpStatus.BAD_REQUEST, authController.register(requestDTOAlreadyExistingEmail).getStatusCode());
    }

    @Test
    public void shouldThrowExceptionWhenPasswordIsInvalid(){
        when(authProvider.register("name@email.com", "1")).thenThrow(IllegalArgumentException.class);

        RegisterRequestDTO requestDTOInvalidPassword = new RegisterRequestDTO("name@email.com", "1");

        assertEquals(HttpStatus.BAD_REQUEST, authController.register(requestDTOInvalidPassword).getStatusCode());
    }

    @Test
    public void shouldRegisterUser(){
        when(authProvider.register(user.getEmail(), user.getPassword())).thenReturn(user);

        assertEquals(HttpStatus.OK, authController.register(requestDTO).getStatusCode());
    }

    @Test
    public void shouldLoginUser(){
        when((authProvider.login(user.getEmail(), user.getPassword()))).thenReturn(Optional.of(user));

        assertEquals(HttpStatus.OK, authController.login(requestDTO).getStatusCode());
    }

    @Test
    public void shouldNotLoginUser(){
        when((authProvider.login(user.getEmail(), user.getPassword()))).thenReturn(Optional.empty());

        assertEquals(HttpStatus.BAD_REQUEST, authController.login((requestDTO)).getStatusCode());
    }

}
