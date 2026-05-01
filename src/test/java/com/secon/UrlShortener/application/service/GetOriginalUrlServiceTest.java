package com.secon.UrlShortener.application.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.secon.UrlShortener.domain.model.Click;
import com.secon.UrlShortener.domain.out.ClickRepository;
import com.secon.UrlShortener.domain.out.UrlRepository;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.usecase.AnalyticsProvider;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
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

    @InjectMocks
    GetOriginalUrlService getOriginalUrlService;

    @Captor
    ArgumentCaptor<Click> argumentCaptor;

    Url url;

    @BeforeEach
    public void setup(){
        url = new Url("https://google.com");
        url.setSlug("dnh");
    }

    @Test
    public void shouldReturnCorrectUrlFromSlug(){
        when(urlRepository.findBySlug("dnh")).thenReturn(Optional.of(url));

        String urlFromService = getOriginalUrlService.originalUrl("dnh", "userAgent", "ipAddress");
        Assertions.assertNotNull(urlFromService);
        Assertions.assertEquals(url.getOriginalUrl(), urlFromService);
    }

    @Test
    public void shouldThrowExceptionWhenSlugIsNotValid(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> getOriginalUrlService.originalUrl("00000000000000000000", "userAgent", "ipAddress"));
    }

    @Test
    public void shouldCreateLogWhenExceptionIsThrow() throws IOException, GeoIp2Exception {
        String ipAddress = "invalidIpAddress";

        when(analyticsProvider.getGeoLocationData(ipAddress)).thenThrow(IOException.class);

        getOriginalUrlService.saveClick(url, "invalidUserAgent", "invalidIpAddress");

        verify(clickRepository, never()).save(any(Click.class));

    }
}
