package com.secon.UrlShortener.outbound.entities;

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
}
