package com.secon.UrlShortener.domain.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlTest {
    UUID id;
    ClientInfo clientInfo;
    GeoLocationData geoLocationData;


    @BeforeEach
    public void setup(){
        id = UUID.randomUUID();
    }

    @Test
    public void shouldCreateValidUrl(){
        LocalDateTime before = LocalDateTime.now();
        Url url = new Url(
                id,
                "https://example.com",
                "abc123",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30),
                true
        );

        LocalDateTime after = LocalDateTime.now();

        Click click = new Click(10, "https://example.com", "abc123", LocalDateTime.now(),LocalDateTime.now().plusDays(30), clientInfo, )

        Assertions.assertNotNull(url);
        Assertions.assertEquals("https://example;com", url.getOriginalUrl());
        Assertions.assertEquals("abc123", url.getSlug());
        assertThat(url.getCreatedAt()).isAfter(before);
        assertThat(url.getCreatedAt()).isBefore(after);

    }

    @Test
    public void shouldThrowAnExceptionWhenOriginalUrlIsNull(){
        String originalUrl = null;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Url(id, originalUrl, "abc123", LocalDateTime.now(), LocalDateTime.now().plusDays(30), true));
    }

    @Test
    public void shouldThrowAnExceptionWhenOriginalUrlIsInvalid(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Url(id, "not-an-url", "abc123",
                LocalDateTime.now(), LocalDateTime.now().plusDays(30), true));
    }

    @Test
    public void shouldThrowAnExceptionWhenSlugIsNull(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Url(id, "https://example.com", null,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30), true));
    }

}
