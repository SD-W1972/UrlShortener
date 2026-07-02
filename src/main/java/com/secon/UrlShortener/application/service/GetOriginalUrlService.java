package com.secon.UrlShortener.application.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.secon.UrlShortener.domain.model.Click;
import com.secon.UrlShortener.domain.model.User;
import com.secon.UrlShortener.domain.out.ClickRepository;
import com.secon.UrlShortener.domain.out.UrlRepository;
import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.out.UserRepository;
import com.secon.UrlShortener.domain.usecase.AnalyticsProvider;
import com.secon.UrlShortener.domain.usecase.GetOriginalUrlUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class GetOriginalUrlService implements GetOriginalUrlUseCase {

    private static final Logger log = LoggerFactory.getLogger(GetOriginalUrlService.class);

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private AnalyticsProvider analyticsProvider;

    @Autowired
    private ClickRepository clickRepository;

    @Cacheable(value = "originalUrl", key = "#slug")
    @Override
    public String originalUrl(String slug, String userAgent, String ipAddress) {
        Url url = urlRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("URL not found"));

        saveClick(url, userAgent, ipAddress);

        return url.getOriginalUrl();

    }

    public void saveClick(Url url, String userAgent, String ipAddress){

        Click click = null;
        try {
            click = new Click(
                    url.getOriginalUrl(),
                    url.getSlug(),
                    LocalDateTime.now(),
                    analyticsProvider.getClientInfo(userAgent),
                    analyticsProvider.getGeoLocationData(ipAddress),
                    ipAddress
            );
        } catch (IOException | GeoIp2Exception e) {
            log.error("Failed to save click for slug: {}, ip: {}", url.getSlug(), ipAddress, e);
        }

        if (click != null) {
            clickRepository.save(click);
        }

    }



}
