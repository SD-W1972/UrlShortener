package com.secon.UrlShortener.application.usecases;

import com.secon.UrlShortener.application.port.in.service.ShortenUrlService;
import com.secon.UrlShortener.application.port.out.UrlRepository;
import com.secon.UrlShortener.domain.model.Url;
import io.seruco.encoding.base62.Base62;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UrlShortenUseCaseTest {

    Url urlEntity;

    @Mock
    UrlRepository urlRepository;

    @InjectMocks
    ShortenUrlService urlShortenService;

    @BeforeEach
    public void setup(){
         urlEntity = new Url(
                "https://google.com"
        );
    }

    @Test
    public void shouldCreateValidSlug(){
        //Simulate db verification: seeing if Url with provided originalUrl already exists
        String url = "https://google.com";
        when(urlRepository.findByOriginalUrl(url)).thenReturn(Optional.empty(Url url));

        //Creating Base63 object
        Base62 base62 = Base62.createInstance();

        //Hardcoding and getting the actual slug from the service method
        byte[] hardcodedArrayOfBytesFromSlug = base62.encode(urlEntity.getOriginalUrl().getBytes(StandardCharsets.UTF_8));
        String actualSlug = urlShortenService.encodeToSlug(urlEntity.getOriginalUrl());

        Assertions.assertNotNull(actualSlug);
        Assertions.assertEquals(new String(hardcodedArrayOfBytesFromSlug), actualSlug);
    }

    @Test
    public void shouldReturnFromDBIfSlugAlreadyExists(){
        String url = "https://google.com";

        Optional<Url> emptyUrlOptional = Optional.empty();
        when(urlRepository.findByOriginalUrl(url)).thenReturn(emptyUrlOptional);

        String alreadyExistingSlug = urlShortenService.encodeToSlug(url);

        Assertions.assertNotNull(alreadyExistingSlug);
        Assertions.assertEquals(urlEntity.getSlug(), alreadyExistingSlug);
    }

}
