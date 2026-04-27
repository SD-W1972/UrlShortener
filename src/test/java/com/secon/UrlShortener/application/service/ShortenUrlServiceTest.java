package com.secon.UrlShortener.application.service;

import com.secon.UrlShortener.domain.out.UrlRepository;
import com.secon.UrlShortener.domain.model.Url;
import io.seruco.encoding.base62.Base62;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.ByteBuffer;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShortenUrlServiceTest {

    Url urlEntity;
    Url savedUrlEntity;

    @Mock
    UrlRepository urlRepository;

    @InjectMocks
    ShortenUrlService urlShortenService;

    @BeforeEach
    public void setup() {
        urlEntity = new Url("https://google.com");

        savedUrlEntity = new Url("https://google.com");
        savedUrlEntity.setId(1L);
    }

    @Test
    public void shouldCreateValidSlug() {
        String url = "https://google.com";

        when(urlRepository.findByOriginalUrl(url)).thenReturn(Optional.empty());
        when(urlRepository.save(any(Url.class))).thenReturn(savedUrlEntity);

        String actualSlug = urlShortenService.encodeToSlug(url);

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(savedUrlEntity.getId());
        Base62 base62 = Base62.createInstance();

        byte[] hardcodedSlug = base62.encode(buffer.array());

        Assertions.assertNotNull(actualSlug);
        Assertions.assertEquals(new String(hardcodedSlug), actualSlug);
    }

    @Test
    public void shouldReturnFromDBIfSlugAlreadyExists() {
        String url = "https://google.com";
        String existingSlug = "abc123";

        Url existingUrl = new Url(url);
        existingUrl.setSlug(existingSlug);

        when(urlRepository.findByOriginalUrl(url)).thenReturn(Optional.of(existingUrl));

        String result = urlShortenService.encodeToSlug(url);

        Assertions.assertEquals(existingSlug, result);
        verify(urlRepository, never()).save(any(Url.class));
    }
}