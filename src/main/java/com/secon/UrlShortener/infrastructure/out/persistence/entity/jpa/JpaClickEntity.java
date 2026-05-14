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
@Table(name = "Click")
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
    private JpaClientInfoAdapter jpaClientInfoAdapter;
    private JpaGeoLocationDataAdapter jpaGeoLocationDataAdapter;
    private String ipAdress;

    public JpaClickEntity(Click click){
        this.id = click.getId();
        this.originalUrl = click.getOriginalUrl();
        this.slug = click.getSlug();
        this.clickedAt = click.getClickedAt();
        this.jpaClientInfoAdapter = new JpaClientInfoAdapter(click.getClientInfo());
        this.jpaGeoLocationDataAdapter = new JpaGeoLocationDataAdapter(click.getGeoLocationData());
        this.ipAdress = click.getIpAdress();
    }
}
