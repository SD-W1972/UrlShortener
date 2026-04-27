package com.secon.UrlShortener.infrastructure.adapter.out.persistence.entities;

import com.secon.UrlShortener.domain.model.Click;
import com.secon.UrlShortener.infrastructure.adapter.out.persistence.ov.JpaClientInfoAdapter;
import com.secon.UrlShortener.infrastructure.adapter.out.persistence.ov.JpaGeoLocationDataAdapter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

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
    }
}
