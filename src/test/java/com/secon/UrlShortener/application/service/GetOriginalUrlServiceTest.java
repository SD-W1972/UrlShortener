package com.secon.UrlShortener.application.service;

import com.secon.UrlShortener.domain.out.UrlRepository;
import com.secon.UrlShortener.domain.model.Url;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetOriginalUrlServiceTest {

    @Mock
    UrlRepository urlRepository;

    @InjectMocks
    GetOriginalUrlService getOriginalUrlService;

    Url url;

    @BeforeEach
    public void setup(){
        url = new Url("https://google.com");
        url.setSlug("dnh");
    }

    @Test
    public void shouldReturnCorrectUrlFromSlug(){
        when(urlRepository.findBySlug("dnh")).thenReturn(Optional.of(url));

        String urlFromService = getOriginalUrlService.originalUrl("dnh");
        Assertions.assertNotNull(urlFromService);
        Assertions.assertEquals(url.getOriginalUrl(), urlFromService);
    }

    @Test
    public void shouldThrowExceptionWhenSlugIsNotValid(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> getOriginalUrlService.originalUrl("00000000000000000000"));
    }
}
