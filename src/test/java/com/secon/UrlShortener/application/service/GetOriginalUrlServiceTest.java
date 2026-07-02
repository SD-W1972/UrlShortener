package com.secon.UrlShortener.application.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.secon.UrlShortener.domain.model.Click;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.out.ClickRepository;
import com.secon.UrlShortener.domain.out.UrlRepository;
import com.secon.UrlShortener.domain.usecase.AnalyticsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetOriginalUrlServiceTest {

    @Mock
    private AnalyticsProvider analyticsProvider;

    @Mock
    private ClickRepository clickRepository;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private GetOriginalUrlService getOriginalUrlService;

    @Captor
    private ArgumentCaptor<Click> argumentCaptor;

    private Url url;

    @BeforeEach
    public void setup() {
        url = new Url("https://google.com");
        url.setSlug("00000001");
    }

    @Test
    public void shouldReturnCorrectUrlFromSlug() {
        when(urlRepository.findBySlug("00000001")).thenReturn(Optional.of(url));

        String urlFromService = getOriginalUrlService.originalUrl("00000001", "userAgent", "ipAddress");

        Assertions.assertNotNull(urlFromService);
        Assertions.assertEquals(url.getOriginalUrl(), urlFromService);

        verify(clickRepository, times(1)).save(any(Click.class));
    }

    @Test
    public void shouldThrowExceptionWhenSlugIsNotValid() {
        when(urlRepository.findBySlug("00000000000000000000")).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> getOriginalUrlService.originalUrl("00000000000000000000", "userAgent", "ipAddress"));
    }

    @Test
    public void shouldSaveClickWhenGeoIpSucceeds() throws IOException, GeoIp2Exception {
        String userAgent = "Mozilla/5.0";
        String ipAddress = "8.8.8.8";

        when(urlRepository.findBySlug("00000001")).thenReturn(Optional.of(url));
        when(analyticsProvider.getClientInfo(userAgent)).thenReturn(null);
        when(analyticsProvider.getGeoLocationData(ipAddress)).thenReturn(null);

        getOriginalUrlService.originalUrl("00000001", userAgent, ipAddress);

        verify(clickRepository, times(1)).save(argumentCaptor.capture());
        Click savedClick = argumentCaptor.getValue();

        Assertions.assertEquals(url.getOriginalUrl(), savedClick.getOriginalUrl());
        Assertions.assertEquals(url.getSlug(), savedClick.getSlug());
        Assertions.assertEquals(ipAddress, savedClick.getIpAdress());
    }

    @Test
    public void shouldSaveClickWithFallbackWhenGeoIpFails() throws IOException, GeoIp2Exception {
        String userAgent = "Mozilla/5.0";
        String ipAddress = "invalidIpAddress";

        when(urlRepository.findBySlug("00000001")).thenReturn(Optional.of(url));
        when(analyticsProvider.getGeoLocationData(ipAddress)).thenThrow(IOException.class);
        when(analyticsProvider.getClientInfo(userAgent)).thenReturn(null);

        Assertions.assertDoesNotThrow(() ->
                getOriginalUrlService.originalUrl("00000001", userAgent, ipAddress)
        );

        verify(clickRepository, times(1)).save(argumentCaptor.capture());
        Click savedClick = argumentCaptor.getValue();

        Assertions.assertNotNull(savedClick);
        Assertions.assertEquals(url.getOriginalUrl(), savedClick.getOriginalUrl());
        Assertions.assertEquals(url.getSlug(), savedClick.getSlug());
        Assertions.assertEquals(ipAddress, savedClick.getIpAdress());
        Assertions.assertNull(savedClick.getClientInfo());
        Assertions.assertNull(savedClick.getGeoLocationData());
    }

    @Test
    public void shouldNotSaveClickWhenUrlNotFound() {
        when(urlRepository.findBySlug("nonexistent")).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> getOriginalUrlService.originalUrl("nonexistent", "userAgent", "ipAddress"));

        verify(clickRepository, never()).save(any(Click.class));
    }

    @Test
    public void shouldCacheOriginalUrl() {
        when(urlRepository.findBySlug("00000001")).thenReturn(Optional.of(url));

        String result1 = getOriginalUrlService.originalUrl("00000001", "userAgent", "ipAddress");

        String result2 = getOriginalUrlService.originalUrl("00000001", "userAgent", "ipAddress");

        Assertions.assertEquals(url.getOriginalUrl(), result1);
        Assertions.assertEquals(url.getOriginalUrl(), result2);

        verify(urlRepository, times(4)).findBySlug("00000001");

        verify(clickRepository, times(2)).save(any(Click.class));
    }
}