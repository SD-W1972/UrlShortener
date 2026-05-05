package com.secon.UrlShortener.application.utilities;

import com.secon.UrlShortener.domain.model.Click;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import com.secon.UrlShortener.domain.model.enums.*;

import com.secon.UrlShortener.infrastructure.out.persistence.entities.JpaClickEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.entities.JpaUrlEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.entities.JpaUserEntity;
import com.secon.UrlShortener.infrastructure.out.persistence.jpa.ov.JpaClientInfoAdapter;
import com.secon.UrlShortener.infrastructure.out.persistence.jpa.ov.JpaGeoLocationDataAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua_parser.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ToDomainTest {

    private String originalUrl = "https://google.com";
    private String slug = "dnh";
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime expiresAt = createdAt.plusDays(30);
    private LocalDateTime clickedAt = LocalDateTime.now();

    private Url url;
    private Click click;
    private User user;
    private JpaUrlEntity urlEntity;
    private JpaClickEntity clickEntity;
    private JpaUserEntity userEntity;
    private ClientInfo clientInfo;
    private GeoLocationData geoLocationData;
    private JpaClientInfoAdapter jpaClientInfoAdapter;
    private JpaGeoLocationDataAdapter jpaGeoLocationDataAdapter;

    @BeforeEach
    public void setup(){
        this.clientInfo = new ClientInfo(
                "Chrome",
                "5.1",
                "Windows",
                "10",
                "device"
        );

        this.geoLocationData =  new GeoLocationData(
                "Brazil",
                "Sao Paulo",
                "01230-200",
                "55.9733",
                "55.9733"
        );

        this.url = new Url(
                originalUrl,
                slug,
                createdAt,
                expiresAt,
                true
        );

        this.click = new Click(
                originalUrl,
                slug,
                clickedAt,
                clientInfo,
               geoLocationData,
                "123.456.789.10"
        );

        user = new User(
            "email@gmail.com",
                "Senha#123#",
                UserType.ADMIN,
                new ArrayList<Url>()
        );

        urlEntity = new JpaUrlEntity(url);
        clickEntity = new JpaClickEntity(click);
        userEntity = new JpaUserEntity(user);
        jpaClientInfoAdapter = new JpaClientInfoAdapter(clientInfo);
        jpaGeoLocationDataAdapter = new JpaGeoLocationDataAdapter(geoLocationData);

    }

    @Test
    public void shouldConvertJpaUrlEntityToUrl(){
        Url urlFromToDomain = ToDomain.toDomainUrl(urlEntity);

        Assertions.assertNotNull(urlFromToDomain);
        Assertions.assertEquals(url, urlFromToDomain);
    }

    @Test
    public void shouldConvertJpaClickEntityToClick(){
        Click clickFromToDomain = ToDomain.toDomainClick(clickEntity);

        Assertions.assertNotNull(clickFromToDomain);
        Assertions.assertEquals(click, clickFromToDomain);
    }

    @Test
    public void shouldConvertJpaUserEntityToUser(){
        User userFromToDomain = ToDomain.toDomainUser(userEntity);

        Assertions.assertNotNull(userFromToDomain);
        Assertions.assertEquals(user, userFromToDomain);
    }

    @Test
    public void shouldConvertFromJpaClientInfoToObjectValueClientInfo(){
        ClientInfo clientInfoFromToDomain = ToDomain.toObjectValueClientInfo(jpaClientInfoAdapter);

        Assertions.assertNotNull(clientInfoFromToDomain);
        Assertions.assertEquals(clientInfo, clientInfoFromToDomain);
    }

    @Test
    public void shouldConvertFromJpaGeoLocationDataToObjectValueGeoLocationData(){
        GeoLocationData geoLocationDataFromToDomain = ToDomain.toObjectValueGeoLocationData(jpaGeoLocationDataAdapter);

        Assertions.assertNotNull(geoLocationDataFromToDomain);
        Assertions.assertEquals(geoLocationData, geoLocationDataFromToDomain);
    }
}
