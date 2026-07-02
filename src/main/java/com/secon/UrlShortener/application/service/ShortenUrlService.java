package com.secon.UrlShortener.application.service;

import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.out.UrlRepository;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.out.UserRepository;
import com.secon.UrlShortener.domain.usecase.ShortenUrlUseCase;
import io.seruco.encoding.base62.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

@Service
public class ShortenUrlService implements ShortenUrlUseCase {

    @Autowired
    private UrlRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String encodeToSlug(String originalUrl) {
        Optional<Url> alreadyExistingUrl = repository.findByOriginalUrl(originalUrl);

        if (alreadyExistingUrl.isEmpty()) {
            Url url = new Url(originalUrl);
            Url savedUrl = repository.save(url);

            Base62 base62 = Base62.createInstance();
            byte[] slugBytes = base62.encode(longToBytes(savedUrl.getId()));
            String slug = new String(slugBytes).trim();
            
            if (slug == null || slug.isEmpty()) {
                throw new RuntimeException("Failed to generate slug");
            }

            savedUrl.setSlug(slug);
            repository.save(savedUrl);
            associateUrlToUser(savedUrl);

            return slug;
        } else {
            Url existing = alreadyExistingUrl.get();
            return existing.getSlug() != null ? existing.getSlug() : "";
        }
    }

    private byte[] longToBytes(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(value);
        return buffer.array();
    }

    private String getCurrentUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return null;
    }

    public void associateUrlToUser(Url savedUrl){
        if(getCurrentUserEmail() == null){ return;}

        Optional<User> user = userRepository.findByEmail(getCurrentUserEmail());
        if(user.isEmpty()) return;

        List<Url> urlList = user.get().getUrls();

        urlList.add(savedUrl);

        user.get().setUrls(urlList);

        userRepository.save(user.get());
    }
}
