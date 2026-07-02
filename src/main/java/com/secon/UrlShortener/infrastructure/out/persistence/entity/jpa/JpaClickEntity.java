package com.secon.UrlShortener.infrastructure.out.persistence.entity.jpa;

import com.secon.UrlShortener.domain.model.Click;
import com.secon.UrlShortener.infrastructure.out.persistence.repository.jpa.ov.JpaClientInfoAdapter;
import com.secon.UrlShortener.infrastructure.out.persistence.repository.jpa.ov.JpaGeoLocationDataAdapter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "click")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaClickEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;
    private String slug;
    private LocalDateTime clickedAt;
    private String ipAdress;

    @Embedded
    private JpaClientInfoAdapter jpaClientInfoAdapter;

    @Embedded
    private JpaGeoLocationDataAdapter jpaGeoLocationDataAdapter;

    public JpaClickEntity(Click click) {
        this.id = click.getId();
        this.originalUrl = click.getOriginalUrl();
        this.slug = click.getSlug();
        this.clickedAt = click.getClickedAt();
        this.ipAdress = click.getIpAdress();
        this.jpaClientInfoAdapter = click.getClientInfo() == null ? null : new JpaClientInfoAdapter(click.getClientInfo());
        this.jpaGeoLocationDataAdapter = click.getGeoLocationData() == null ? null : new JpaGeoLocationDataAdapter(click.getGeoLocationData());
    }
}