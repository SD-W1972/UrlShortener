package com.secon.UrlShortener.application.service;

import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.out.UrlRepository;
import com.secon.UrlShortener.domain.out.UserRepository;
import com.secon.UrlShortener.domain.usecase.ShortenUrlUseCase;
import com.secon.UrlShortener.infrastructure.out.persistence.entity.jpa.JpaUrlEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.entity.jpa.JpaUserEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.repository.jpa.JpaUrlRepository;
import com.secon.UrlShortener.infrastructure.out.persistence.repository.jpa.JpaUserRepository;
import io.seruco.encoding.base62.Base62;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.util.Optional;

@Service
public class ShortenUrlService implements ShortenUrlUseCase {

    private static final Logger log = LoggerFactory.getLogger(ShortenUrlService.class);

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private JpaUrlRepository jpaUrlRepository;

    @Override
    @Transactional
    public String encodeToSlug(String originalUrl) {
        Optional<Url> existingUrl = urlRepository.findByOriginalUrl(originalUrl);

        if (existingUrl.isPresent()) {
            Url url = existingUrl.get();
            if (url.getSlug() == null || url.getSlug().isEmpty()) {
                String slug = generateSlug(url);
                url.setSlug(slug);
                urlRepository.save(url);
            }
            associateUrlToUser(url);
            return url.getSlug();
        }

        User currentUser = getCurrentUser();

        Url url = new Url(originalUrl);

        Url savedUrl = urlRepository.save(url);

        String slug = generateSlug(savedUrl);
        savedUrl.setSlug(slug);
        Url finalUrl = urlRepository.save(savedUrl);

        if (currentUser != null) {
            associateUrlToUser(finalUrl);
        }

        return finalUrl.getSlug();
    }

    private String generateSlug(Url url) {
        Base62 base62 = Base62.createInstance();
        byte[] slugBytes = base62.encode(longToBytes(url.getId()));
        String slug = new String(slugBytes).trim();
        if (slug == null || slug.isEmpty()) {
            throw new RuntimeException("Failed to generate slug for URL ID: " + url.getId());
        }
        return slug;
    }

    private byte[] longToBytes(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(value);
        return buffer.array();
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElse(null);
    }

    private void associateUrlToUser(Url url) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return;
        }

        JpaUserEntity userEntity = jpaUserRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        JpaUrlEntity urlEntity = jpaUrlRepository.findById(url.getId())
                .orElseThrow(() -> new RuntimeException("URL not found"));

        if (urlEntity.getUser() != null && urlEntity.getUser().getId().equals(userEntity.getId())) {
            return;
        }

        urlEntity.setUser(userEntity);
        jpaUrlRepository.save(urlEntity);
    }
}