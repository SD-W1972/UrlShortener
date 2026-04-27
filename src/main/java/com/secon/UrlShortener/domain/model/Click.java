package com.secon.UrlShortener.domain.model;

import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.UUID;

public class Click {

    private final Long id;
    private String originalUrl;
    private String slug;
    private LocalDateTime clickedAt;
    private ClientInfo clientInfo;
    private GeoLocationData geoLocationData;
    private String ipAdress;

    public Click(String originalUrl, String slug, LocalDateTime clickedAt, ClientInfo clientInfo, GeoLocationData geoLocationData, String ipAdress) {
        validate(originalUrl, slug, clickedAt);
        this.id = null;
        this.originalUrl = originalUrl;
        this.slug = slug;
        this.clickedAt = clickedAt;
        this.clientInfo = clientInfo;
        this.geoLocationData = geoLocationData;
        this.ipAdress = ipAdress;
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

    public LocalDateTime getClickedAt() {
        return clickedAt;
    }

    public void setClickedAt(LocalDateTime clickedAt) {
        this.clickedAt = clickedAt;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public GeoLocationData getGeoLocationData() {
        return geoLocationData;
    }

    public void setGeoLocationData(GeoLocationData geoLocationData) {
        this.geoLocationData = geoLocationData;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    private void validate(String originalUrl, String slug, LocalDateTime clickedAt) {
        if (originalUrl == null || originalUrl.isBlank()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        try {
            URI uri = new URI(originalUrl);
            String scheme = uri.getScheme();

            if (scheme == null || (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https"))) {
                throw new IllegalArgumentException("URL invalid: must use http or https");
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format: " + originalUrl, e);
        }

        if (slug == null || slug.isBlank()) {
            throw new IllegalArgumentException("Slug cannot be null or empty");
        }

        if (clickedAt == null) {
            throw new IllegalArgumentException("Data of the click cannot be null");
        }
    }

    @Override
    public String toString() {
        return "Click{" +
                "id=" + id +
                ", originalUrl='" + originalUrl + '\'' +
                ", slug='" + slug + '\'' +
                ", clickedAt=" + clickedAt +
                ", clientInfo=" + clientInfo +
                ", geoLocationData=" + geoLocationData +
                ", ipAdress='" + ipAdress + '\'' +
                '}';
    }
}