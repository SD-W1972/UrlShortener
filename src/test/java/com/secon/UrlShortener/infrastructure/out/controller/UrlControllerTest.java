package com.secon.UrlShortener.infrastructure.out.controller;

import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.usecase.AnalyticsProvider;
import com.secon.UrlShortener.domain.usecase.GetOriginalUrlUseCase;
import com.secon.UrlShortener.domain.usecase.ShortenUrlUseCase;
import io.seruco.encoding.base62.Base62;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlControllerTest {

    @Mock
    private ShortenUrlUseCase shortenUrl;

    @Mock
    private GetOriginalUrlUseCase getOriginalUrl;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UrlController urlController;

    private Url url;
    private String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36";
    private String ipAddress = "192.168.1.1";
    private String expectedSlug;

    @BeforeEach
    public void setup(){
        url = new Url("https://google.com");
        url.setId(1L);

        //getting expected slug
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(url.getId());
        Base62 base62 = Base62.createInstance();
        byte[] hardcodedSlug = base62.encode(buffer.array());

        expectedSlug = new String(hardcodedSlug);
    }

    @Test
    public void shouldReturnResponseEntityWithSlug(){
        ResponseEntity<String> expectedResponse = ResponseEntity.status(201).body(expectedSlug);

        when(shortenUrl.encodeToSlug("https://google.com")).thenReturn(expectedSlug);

        ResponseEntity<String> actualResponseSlug = urlController.getSlug(url.getOriginalUrl());

        assertNotNull(actualResponseSlug);
        assertEquals(expectedResponse, actualResponseSlug);
    }

    @Test
    public void shouldThrowExceptionWhenOriginalUrlIsNotValid(){
        when(shortenUrl.encodeToSlug("INVALID URL")).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class,
                () -> urlController.getSlug("INVALID URL"));
    }

    @Test
    public void shouldReturnCorrectUrl(){
        when(getOriginalUrl.originalUrl(expectedSlug, userAgent, ipAddress)).thenReturn(url.getOriginalUrl());
        ResponseEntity<String> expectedUrl = ResponseEntity.status(HttpStatus.FOUND).header("Location", "https://google.com").build();

        when(request.getHeader("User-Agent")).thenReturn(userAgent);
        when(request.getRemoteAddr()).thenReturn(ipAddress);

        ResponseEntity<String> responseUrl = urlController.getOriginalUrl(expectedSlug, request);

        assertNotNull(responseUrl);
        assertEquals(expectedUrl, responseUrl);
    }

    @Test
    public void shouldThrowExceptionWhenUserAgentIsInvalid(){
        when(getOriginalUrl.originalUrl(expectedSlug, "INVALID USER AGENT", ipAddress)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () ->
                getOriginalUrl.originalUrl(expectedSlug, "INVALID USER AGENT", ipAddress));
    }

    @Test
    public void shouldThrowExceptionWhenIpAddressIsInvalid(){
        when(getOriginalUrl.originalUrl(expectedSlug, userAgent, "INVALID IP ADDRESS")).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () ->
                getOriginalUrl.originalUrl(expectedSlug, userAgent, "INVALID IP ADDRESS"));
    }
}
