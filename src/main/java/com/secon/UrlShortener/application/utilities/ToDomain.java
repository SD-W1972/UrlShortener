package com.secon.UrlShortener.application.utilities;

import com.secon.UrlShortener.domain.model.Click;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import com.secon.UrlShortener.infrastructure.out.persistence.entity.jpa.JpaClickEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.entity.jpa.JpaUrlEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.entity.jpa.JpaUserEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.repository.jpa.ov.JpaClientInfoAdapter;
import com.secon.UrlShortener.infrastructure.out.persistence.repository.jpa.ov.JpaGeoLocationDataAdapter;

import java.util.stream.Collectors;

public class ToDomain {

    public static Url toDomainUrl(JpaUrlEntity entity) {
        if (entity == null) return null;
        Url url = new Url(
                entity.getOriginalUrl(),
                entity.getSlug(),
                entity.getCreatedAt(),
                entity.getExpiresAt(),
                entity.isActive()
        );
        url.setId(entity.getId());
        return url;
    }

    public static Click toDomainClick(JpaClickEntity entity) {
        if (entity == null) return null;
        return new Click(
                entity.getOriginalUrl(),
                entity.getSlug(),
                entity.getClickedAt(),
                toObjectValueClientInfo(entity.getJpaClientInfoAdapter()),
                toObjectValueGeoLocationData(entity.getJpaGeoLocationDataAdapter()),
                entity.getIpAdress()
        );
    }

    public static User toDomainUser(JpaUserEntity entity) {
        if (entity == null) return null;
        return new User(
                entity.getEmail(),
                entity.getPassword(),
                entity.getUserType(),
                entity.getUrls().stream()
                        .map(ToDomain::toDomainUrl)
                        .collect(Collectors.toUnmodifiableList())
        );
    }

    public static ClientInfo toObjectValueClientInfo(JpaClientInfoAdapter jcia) {
        if (jcia == null) {
            return ClientInfo.unknown();
        }
        return new ClientInfo(
                jcia.getBrowser(),
                jcia.getBrowserVersion(),
                jcia.getOS(),
                jcia.getOSVersion(),
                jcia.getDevice()
        );
    }

    public static GeoLocationData toObjectValueGeoLocationData(JpaGeoLocationDataAdapter jglda) {
        if (jglda == null) {
            return GeoLocationData.unknown();
        }
        return new GeoLocationData(
                jglda.getCountry(),
                jglda.getCity(),
                jglda.getPostal(),
                jglda.getLatitude(),
                jglda.getLongitude()
        );
    }
}