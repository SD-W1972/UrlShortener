package com.secon.UrlShortener.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlTest {

    UUID id;

    @BeforeEach
    public void setup() {
        id = UUID.randomUUID();
    }

    @Test
    public void shouldCreateValidUrl() {
        LocalDateTime before = LocalDateTime.now();

        Url url = new Url(
                id,
                "https://google.com",
                "abc123",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30),
                true
        );

        LocalDateTime after = LocalDateTime.now();

        Assertions.assertNotNull(url);
        Assertions.assertEquals("https://google.com", url.getOriginalUrl());
        Assertions.assertEquals("abc123", url.getSlug());
        assertThat(url.getCreatedAt()).isBetween(before, after);
    }

    @Test
    public void shouldThrowAnExceptionWhenOriginalUrlIsNull() {
        String originalUrl = null;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Url(id, originalUrl, "abc123", LocalDateTime.now(), LocalDateTime.now().plusDays(30), true));
    }

    @Test
    public void shouldThrowAnExceptionWhenOriginalUrlIsInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Url(id, "not-an-url", "abc123",
                LocalDateTime.now(), LocalDateTime.now().plusDays(30), true));
    }

    @Test
    public void shouldThrowAnExceptionWhenSlugIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Url(id, "https://google.com", null,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30), true));
    }

}