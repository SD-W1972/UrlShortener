package com.secon.UrlShortener.outbound.entities;

import com.secon.UrlShortener.outbound.ov.JpaClientInfoAdapter;
import com.secon.UrlShortener.outbound.ov.JpaGeoLocationDataAdapter;
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
@Table(name = "Click")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaClickEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String originalUrl;
    private String slug;
    private LocalDateTime clickedAt;
    private JpaClientInfoAdapter jpaClientInfoAdapter;
    private JpaGeoLocationDataAdapter jpaGeoLocationDataAdapter;
    private String ipAdress;

}
