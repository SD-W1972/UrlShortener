package com.secon.UrlShortener.domain.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import java.time.LocalDateTime;

public class Url {
    private final UUID id;
    private String OriginalUrl;
    private String Slug;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean isActive;

    public Url(UUID id, String originalUrl, String slug, LocalDateTime createdAt, LocalDateTime expiresAt, boolean isActive){
        this.id = id;
        try {
            validate(originalUrl, slug);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        OriginalUrl = originalUrl;
        Slug = slug;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.isActive = isActive;
    }

    public UUID getId() {
        return id;
    }

    public String getOriginalUrl() {
        return OriginalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        OriginalUrl = originalUrl;
    }

    public String getSlug() {
        return Slug;
    }

    public void setSlug(String slug) {
        Slug = slug;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void validate(String originalUrl, String slug) throws URISyntaxException {
        URI uri = new URI(originalUrl);

        if(originalUrl == null){
            throw new IllegalArgumentException("URL is null");
        }

        if(uri.getScheme() != null &&
                (uri.getScheme().equalsIgnoreCase("http") ||
                        uri.getScheme().equalsIgnoreCase("https"))){
            throw new IllegalArgumentException("URL invalid");
        }

        if(slug == null){
            throw new IllegalArgumentException("Slug is null");
        }
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", OriginalUrl='" + OriginalUrl + '\'' +
                ", Slug='" + Slug + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                ", isActive=" + isActive +
                '}';
    }
}
