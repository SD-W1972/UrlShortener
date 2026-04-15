package com.secon.UrlShortener.domain.model;

import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import org.springframework.cglib.core.Local;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.UUID;

public class Click {

    private final UUID id;
    private String originalUrl;
    private String slug;
    private LocalDateTime clickedAt;
    private ClientInfo clientInfo;
    private GeoLocationData geoLocationData;
    private String ipAdress;

    public Click(UUID id, String originalUrl, String slug, LocalDateTime clickedAt, ClientInfo clientInfo, GeoLocationData geoLocationData, String ipAdress) {
        this.id = id;
        try {
            validate(originalUrl, slug, clickedAt);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        this.originalUrl = originalUrl;
        this.slug = slug;
        this.clickedAt = clickedAt;
        this.clientInfo = clientInfo;
        this.geoLocationData = geoLocationData;
        this.ipAdress = ipAdress;
    }

    public UUID getId() {
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

    public void validate(String originalUrl, String slug, LocalDateTime clickedAt) throws URISyntaxException {
        URI uri = new URI(originalUrl);

        if(originalUrl == null || originalUrl.isBlank() || originalUrl.isEmpty()){
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        if(uri.getScheme() != null &&
                (uri.getScheme().equalsIgnoreCase("http") ||
                        uri.getScheme().equalsIgnoreCase("https"))){
            throw new IllegalArgumentException("URL invalid");
        }

        if(slug == null || slug.isBlank() || slug.isEmpty()){
            throw new IllegalArgumentException("Slug cannot be null or empty");
        }

        if(clickedAt == null){
            throw new IllegalArgumentException("Data of the click cannot be null or empty");
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
