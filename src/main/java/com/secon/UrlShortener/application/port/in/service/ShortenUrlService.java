package com.secon.UrlShortener.application.port.in.service;

import com.secon.UrlShortener.application.port.out.UrlRepository;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.model.usecase.ShortenUrlUseCase;
import io.seruco.encoding.base62.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShortenUrlService implements ShortenUrlUseCase {

    @Autowired
    private UrlRepository repository;

    @Override
    public String encodeToSlug(String originalUrl) {
        Optional<Url> alreadyExistingUrl = repository.findByOriginalUrl(originalUrl);

        if (alreadyExistingUrl.isEmpty()) {
            Url url = new Url(originalUrl);
            Url savedUrl = repository.save(url);

            Base62 base62 = Base62.createInstance();
            byte[] slugBytes = base62.encode(uuidToBytes(savedUrl.getId()));
            String slug = new String(slugBytes);

            savedUrl.setSlug(slug);
            repository.save(savedUrl);

            return slug;
        } else {
            return alreadyExistingUrl.get().getSlug();
        }
    }

    private byte[] uuidToBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.allocate(16);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }}
