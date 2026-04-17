package com.secon.UrlShortener.infrastructure.adapter.out.persistence.entities;

import com.secon.UrlShortener.domain.model.Url;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "URL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JpaUrlEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String originalUrl;
    private String slug;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean isActive;

    public JpaUrlEntity(Url url){
        this.originalUrl = url.getOriginalUrl();
        this.slug = url.getSlug();
        this.createdAt = url.getCreatedAt();
        this.expiresAt = url.getExpiresAt();
        this.isActive = url.isActive();
    }

}
