package com.secon.UrlShortener.application.utilities;

import com.secon.UrlShortener.domain.model.Click;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import com.secon.UrlShortener.infrastructure.out.persistence.entities.JpaClickEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.entities.JpaUrlEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.entities.JpaUserEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.jpa.ov.JpaClientInfoAdapter;
import com.secon.UrlShortener.infrastructure.out.persistence.jpa.ov.JpaGeoLocationDataAdapter;

import java.util.stream.Collectors;

public class ToDomain {
    public static Url toDomainUrl(JpaUrlEntity entity){
        return new Url(

                entity.getOriginalUrl(),
                entity.getSlug(),
                entity.getCreatedAt(),
                entity.getExpiresAt(),
                entity.isActive()
        );
    }

    public static Click toDomainClick(JpaClickEntity entity){
        return new Click(
                entity.getOriginalUrl(),
                entity.getSlug(),
                entity.getClickedAt(),
                toObjectValueClientInfo(entity.getJpaClientInfoAdapter()),
                toObjectValueGeoLocationData(entity.getJpaGeoLocationDataAdapter()),
                entity.getIpAdress()
        );
    }

    public static User toDomainUser(JpaUserEntity entity){
        return new User(
                entity.getEmail(),
                entity.getPassword(),
                entity.getUserType(),
                entity.getUrls().stream()
                        .map(url -> toDomainUrl(url))
                        .collect(Collectors.toUnmodifiableList())
        );
    }

    public static ClientInfo toObjectValueClientInfo(JpaClientInfoAdapter jcia){
        return new ClientInfo(
                jcia.getBrowser(),
                jcia.getBrowserVersion(),
                jcia.getOS(),
                jcia.getOSVersion(),
                jcia.getDevice()
        );
    }

    public static GeoLocationData toObjectValueGeoLocationData(JpaGeoLocationDataAdapter jglda){
        return new GeoLocationData(
                jglda.getCountry(),
                jglda.getCity(),
                jglda.getPostal(),
                jglda.getLatitude(),
                jglda.getLongitude()
        );
    }
}
