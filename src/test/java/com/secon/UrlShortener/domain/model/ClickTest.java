package com.secon.UrlShortener.domain.model;

import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClickTest {

    ClientInfo clientInfo;
    GeoLocationData geoLocationData;
    UUID id;
    @BeforeEach
    public void setup(){
        clientInfo = new ClientInfo("Firefox", "149.0.2", "Arch Linux x86_64", "6.19.10-arch1-1", "20DSS27P00 (ThinkPad L450)");
        geoLocationData = new GeoLocationData("Brazil", "Osasco/SP", "00000123", "44.9733", "-93.2323");
        id = new UUID().randomUUID();
    }

    @Test
    public void shouldCreateValidClick(){
        LocalDateTime before = LocalDateTime.now();

        Click click = new Click(
                id,
                "https://example.com",
                "abc123",
                LocalDateTime.now(),
                clientInfo,
                geoLocationData,
                "181.250.130.87"
        );

        LocalDateTime after = LocalDateTime.now();

        assertNotNull(click);
        assertEquals(id, click.getId());
        assertEquals("https://example.com", click.getOriginalUrl());
        assertEquals("abc123", click.getSlug());
        assertEquals("181.250.130.87", click.getIpAdress());
        assertEquals(geoLocationData, click.getGeoLocationData());
        assertEquals(clientInfo, click.getClientInfo());
        assertThat(click.getClickedAt()).isAfter(before);
        assertThat(click.getClickedAt()).isBefore(after);
    }

    @Test
    public void shouldThrowAnExceptionWhenOriginalUrlIsNull(){
        String originalUrl = null;

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new Click(id, originalUrl, "abc123", LocalDateTime.now(),
                        clientInfo, geoLocationData, "181.250.130.87"));
    }

    @Test
    public void shouldThrowAnExceptionWhenOriginalUrlIsInvalid(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new Click(id, "not-an-url", "abc123", LocalDateTime.now(),
                        clientInfo, geoLocationData, "181.250.130.87"));
    }

    @Test
    public void shouldThrowAnExceptionWhenSlugIsNull(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new Click(id, "https://example.com", null, LocalDateTime.now(),
                        clientInfo, geoLocationData, "181.250.130.87"));

    }

    @Test
    public void shouldThrowAnExceptionWhenClickedAtIsNull(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new Click(id, "https://example.com", "abc123", null,
                        clientInfo, geoLocationData, "181.250.130.87"));

    }
}
