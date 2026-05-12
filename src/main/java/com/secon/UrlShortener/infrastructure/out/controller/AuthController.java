package com.secon.UrlShortener.infrastructure.out.controller;

import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.usecase.AuthProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public class AuthController {

    private final AuthProvider authProvider;

    public AuthController(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    @PostMapping("/login")
    public ResponseEntity register(@RequestBody String email, @RequestBody String password){
        try{
            User user = authProvider.register(email, password);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("Message", "User created succesfully"));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("Message", "Email or password invalid"));
        }
    }
}
