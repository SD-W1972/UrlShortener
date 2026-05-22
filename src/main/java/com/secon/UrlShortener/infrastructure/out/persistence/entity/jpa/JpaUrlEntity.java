package com.secon.UrlShortener.infrastructure.out.persistence.entity.jpa;

import com.secon.UrlShortener.domain.model.Url;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "URL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JpaUrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;
    private String slug;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean isActive;

    @ManyToOne
    private JpaUserEntity user;

    public JpaUrlEntity(Url url){
        this.originalUrl = url.getOriginalUrl();
        this.slug = url.getSlug();
        this.createdAt = url.getCreatedAt();
        this.expiresAt = url.getExpiresAt();
        this.isActive = url.isActive();
    }

}
