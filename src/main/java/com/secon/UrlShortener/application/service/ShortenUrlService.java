package com.secon.UrlShortener.application.service;

import com.secon.UrlShortener.domain.out.UrlRepository;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.usecase.ShortenUrlUseCase;
import io.seruco.encoding.base62.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Optional;

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
            byte[] slugBytes = base62.encode(longToBytes(savedUrl.getId()));
            String slug = new String(slugBytes);

            savedUrl.setSlug(slug);
            repository.save(savedUrl);

            return slug;
        } else {
            return alreadyExistingUrl.get().getSlug();
        }
    }

    private byte[] longToBytes(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(value);
        return buffer.array();
    }
}
