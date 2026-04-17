package com.secon.UrlShortener.application.usecases;

import com.secon.UrlShortener.domain.model.Url;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
public class UrlShortenUseCaseTest {

    @Mock
    UrlRepository urlRepository;

    @InjectMocks
    UrlShortenService urlShortenService;

    @Test
    private void shouldCreateValidSlug(){
        String url = "https://google.com";
        when(urlRepository.findByOriginalUrl(url)).thenReturn(Optional.empty());

        String hardcodedSlug = Base64.getUrlEncoder().encodeToString(url.getBytes(StandardCharsets.UTF_8));
        String actualSlug = urlShortenUseCase.generateSlug(url);

        Assertions.assertNotNull(actualSlug);
        Assertions.assertEquals(hardcodedSlug, actualSlug);
    }

    @Test
    private void shouldReturnFromDBIfSlugAlreadyExists(){
        String url = "https://google.com";
        Url urlEntity = new Url(
                UUID.randomUUID(),
                url,
                Base64.getUrlEncoder().encodeToString(url.getBytes()),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30),
                true
        );

        when(urlRepository.findByOriginalUrl(url)).thenReturn(Optional.of(urlEntity));

        String alreadyExistingSlug = urlShortenUseCase.generateSlug(url);
        Assertions.assertNotNull(alreadyExistingSlug);
        Assertions.assertEquals(urlEntity.getSlug(), alreadyExistingSlug);
    }

}
