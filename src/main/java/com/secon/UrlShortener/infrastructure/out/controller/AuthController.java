package com.secon.UrlShortener.infrastructure.out.controller;

import com.secon.UrlShortener.domain.model.DTOS.RegisterRequestDTO;
import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.usecase.AuthProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

public class AuthController {

    private final AuthProvider authProvider;

    public AuthController(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO request){
        try{
            authProvider.register(request.email(), request.password());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("Message", "User created succesfully"));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("Message", "Email or password invalid"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody RegisterRequestDTO request){
        Optional<User> user = authProvider.login(request.email(), request.password());

        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("Message", "User logged in succesfully"));

        }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("Message", "Invalid password or email"));

    }
}
