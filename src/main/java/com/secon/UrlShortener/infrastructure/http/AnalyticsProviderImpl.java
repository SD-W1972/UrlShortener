package com.secon.UrlShortener.infrastructure.http;

import com.maxmind.geoip2.WebServiceClient;
import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import com.secon.UrlShortener.domain.usecase.AnalyticsProvider;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import ua_parser.Parser;

public class AnalyticsProviderImpl implements AnalyticsProvider {

    @Value("${geoip2.account.id}")
    private int accountId;

    @Value("${geoip2.license.key}")
    private String licenseKey;

    private WebServiceClient client;
    private  Parser parser;

    public AnalyticsProviderImpl() {
    }

    @PostConstruct
    public void init(){
        this.client = new WebServiceClient.Builder(accountId, licenseKey).build();
        this.parser = new Parser();
    }

    @Override
    public ClientInfo getClientInfo(String userAgent) {
        return null;
    }

    @Override
    public GeoLocationData getGeoLocationData(String ipAddress) {
        return null;
    }
}
