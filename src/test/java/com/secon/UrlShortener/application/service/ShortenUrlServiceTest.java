package com.secon.UrlShortener.application.service;

import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.model.enums.UserType;
import com.secon.UrlShortener.domain.out.UrlRepository;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.out.UserRepository;
import io.seruco.encoding.base62.Base62;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShortenUrlServiceTest {
    private Url urlEntity;
    private Url savedUrlEntity;
    private User user;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ShortenUrlService shortenUrlService;

    @BeforeEach
    public void setup() {
        urlEntity = new Url("https://google.com");

        savedUrlEntity = new Url("https://google.com");
        savedUrlEntity.setId(1L);

        user = new User(
                "user@email.com",
                "password",
                UserType.CLIENT,
                new ArrayList<Url>()
        );
    }

    @Test
    public void shouldCreateValidSlug() {
        try (MockedStatic<SecurityContextHolder> mockedSecurity = mockStatic(SecurityContextHolder.class)) {
            mockedSecurity.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn(null);

            String url = "https://google.com";

            when(urlRepository.findByOriginalUrl(url)).thenReturn(Optional.empty());
            when(urlRepository.save(any(Url.class))).thenReturn(savedUrlEntity);

            String actualSlug = shortenUrlService.encodeToSlug(url);

            ByteBuffer buffer = ByteBuffer.allocate(8);
            buffer.putLong(savedUrlEntity.getId());
            Base62 base62 = Base62.createInstance();
            byte[] hardcodedSlug = base62.encode(buffer.array());

            Assertions.assertNotNull(actualSlug);
            Assertions.assertEquals(new String(hardcodedSlug), actualSlug);
        }
    }

    @Test
    public void shouldReturnFromDBIfSlugAlreadyExists() {
        String url = "https://google.com";
        String existingSlug = "abc123";

        Url existingUrl = new Url(url);
        existingUrl.setSlug(existingSlug);

        when(urlRepository.findByOriginalUrl(url)).thenReturn(Optional.of(existingUrl));

        String result = shortenUrlService.encodeToSlug(url);

        Assertions.assertEquals(existingSlug, result);
        verify(urlRepository, never()).save(any(Url.class));
    }

    @Test
    public void shouldAddUrlToUsersCollectionOfUrlsIfUserIsLogged(){
        try(MockedStatic<SecurityContextHolder> mockedSecurity = mockStatic(SecurityContextHolder.class)){
            mockedSecurity.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("user@email.com");
            when(userRepository.save(user)).thenReturn(user);

            when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));

            when(urlRepository.findByOriginalUrl("https://google.com")).thenReturn(Optional.empty());
            when(urlRepository.save(any(Url.class))).thenReturn(savedUrlEntity);

            shortenUrlService.encodeToSlug("https://google.com");

            Assertions.assertFalse(user.getUrls().isEmpty());
        }
    }
}