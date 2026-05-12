package com.secon.UrlShortener.infrastructure.out.controller;

import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.usecase.ShortenUrlUseCase;
import io.seruco.encoding.base62.Base62;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlControllerTest {

    @Mock
    private ShortenUrlUseCase shortenUrlUseCase;

    @InjectMocks
    private UrlController urlController;

    private Url url;

    @BeforeEach
    public void setup(){
        url = new Url("https://google.com");
        url.setId(1L);
    }

    @Test
    public void shouldReturnResponseEntityWithSlug(){
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(url.getId());
        Base62 base62 = Base62.createInstance();
        byte[] hardcodedSlug = base62.encode(buffer.array());

        ResponseEntity<String> expectedResponse = ResponseEntity.status(201).body(new String(hardcodedSlug));

        when(shortenUrlUseCase.encodeToSlug("https://google.com")).thenReturn(new String(hardcodedSlug));

        ResponseEntity<String> actualResponseSlug = urlController.getSlug(url.getOriginalUrl());

        assertNotNull(actualResponseSlug);
        assertEquals(expectedResponse, actualResponseSlug);
    }

    @Test
    public void shouldThrowExceptionWhenOriginalUrlIsNotValid(){
        when(shortenUrlUseCase.encodeToSlug("INVALID URL")).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class,
                () -> urlController.getSlug("INVALID URL"));
    }
}
