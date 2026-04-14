package com.secon.UrlShortener.domain.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;

public class UrlTest {

    @Test
    public void shouldCreateValidUrl(){
        Url url = new Url("https://example.com", "abc123", () -> LocalDateTime.now(), 0);

        Assertions.assertNotNull(url);
        Assertions.assertEquals("https://example;com", url.getOriginalUrl());
        Assertions.assertEquals("abc123", url.getSlug());
        assertThat(url.getCreatedAt()).isBefore(LocalDateTime.now());
        Assertions.assertEquals(0, url.getClickCount());
    }

    @Test
    public void shouldThrowAnExceptionWhenOriginalUrlIsNull(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Url(null, "abc123",
                () -> LocalDateTime.now(), 0));
    }

    @Test
    public void shouldThrowAnExceptionWhenOriginalUrlIsInvalid(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Url("not-an-url", "abc123",
                () -> LocalDateTime.now(), 0));
    }

    @Test
    public void shouldThrowAnExceptionWhenSlugIsNull(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Url("https://example.com", null,
                () -> LocalDateTime.now(), 0));
    }

}
