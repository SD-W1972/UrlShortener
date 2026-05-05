package com.secon.UrlShortener.domain.model;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Url {
    private Long id;
    private String originalUrl;
    private String slug;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean isActive;

    public Url(String originalUrl, String slug, LocalDateTime createdAt, LocalDateTime expiresAt, boolean isActive){
        this.id = null;
        try {
            validate(originalUrl, slug);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        this.originalUrl = originalUrl;
        this.slug = slug;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.isActive = isActive;
    }

    public Url(String originalUrl){
        this.originalUrl = originalUrl;
        this.createdAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now().plusDays(30);
        this.id = null;
        this.slug = null;
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public void setId(Long id){ this.id = id;}

    public void validate(String originalUrl, String slug) throws URISyntaxException {
        if (originalUrl == null) {
            throw new IllegalArgumentException("URL is null");
        }

        URI uri = new URI(originalUrl);
        String scheme = uri.getScheme();

        if (scheme == null ||
                (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https"))) {
            throw new IllegalArgumentException("URL invalid: must use http or https");
        }

        if (slug == null) {
            throw new IllegalArgumentException("Slug is null");
        }
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", OriginalUrl='" + originalUrl + '\'' +
                ", Slug='" + slug + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Url url = (Url) o;
        return isActive == url.isActive && Objects.equals(id, url.id) && Objects.equals(originalUrl, url.originalUrl) && Objects.equals(slug, url.slug) && Objects.equals(createdAt, url.createdAt) && Objects.equals(expiresAt, url.expiresAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalUrl, slug, createdAt, expiresAt, isActive);
    }
}
