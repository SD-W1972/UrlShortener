package com.secon.UrlShortener.infrastructure.analytics;

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import com.secon.UrlShortener.domain.usecase.AnalyticsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.OS;
import ua_parser.Parser;
import ua_parser.UserAgent;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Component
public class AnalyticsProviderImpl implements AnalyticsProvider {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsProviderImpl.class);

    private final Parser parser;
    private final DatabaseReader reader;

    @Autowired
    public AnalyticsProviderImpl(@Value("classpath:GeoLite2-City.mmdb") Resource databaseFile) {
        this.parser = new Parser();

        DatabaseReader tempReader = null;
        try {
            if (databaseFile.exists()) {
                tempReader = new DatabaseReader.Builder(databaseFile.getFile())
                        .withCache(new CHMCache())
                        .build();
                log.info("GeoIP2 database loaded");
            } else {
                log.warn("GeoIP2 database not found");
            }
        } catch (IOException e) {
            log.error("Failed to load GeoIP2 database: {}", e.getMessage());
        }
        this.reader = tempReader;
    }

    public AnalyticsProviderImpl(Parser parser, DatabaseReader reader) {
        this.parser = parser;
        this.reader = reader;
    }

    @Override
    public ClientInfo getClientInfo(String userAgent) {
        if (userAgent == null || userAgent.isBlank()) {
            return ClientInfo.unknown();
        }

        try {
            Client client = parser.parse(userAgent);

            String browser = client.userAgent.family;
            String browserVersion = getBrowserVersion(client.userAgent);
            String os = client.os.family;
            String osVersion = getOsVersion(client.os);
            String device = client.device.family;

            return new ClientInfo(
                    getOrDefault(browser),
                    browserVersion,
                    getOrDefault(os),
                    osVersion,
                    getOrDefault(device)
            );
        } catch (Exception e) {
            log.error("Failed to parse user agent: {}", userAgent, e);
            return ClientInfo.unknown();
        }
    }

    public String getBrowserVersion(UserAgent ua) {
        if (ua == null || ua.major == null) {
            return "unknown";
        }
        String version = ua.major;
        if (ua.minor != null) {
            version += "." + ua.minor;
        }
        if (ua.patch != null) {
            version += "." + ua.patch;
        }
        return version;
    }

    public String getOsVersion(OS os) {
        if (os == null || os.major == null) {
            return "unknown";
        }
        String version = os.major;
        if (os.minor != null) {
            version += "." + os.minor;
        }
        if (os.patch != null) {
            version += "." + os.patch;
        }
        if (os.patchMinor != null) {
            version += "." + os.patchMinor;
        }
        return version;
    }

    public String getOrDefault(String value) {
        return value != null && !value.isEmpty() && !value.equals("null") ? value : "unknown";
    }

    @Override
    public GeoLocationData getGeoLocationData(String ipAddress) throws IOException, GeoIp2Exception {
        if (reader == null) {
            log.warn("GeoIP2 database not available, returning unknown location for IP: {}", ipAddress);
            return GeoLocationData.unknown();
        }

        try {
            CityResponse cityResponse = reader.city(InetAddress.getByName(ipAddress));
            return new GeoLocationData(
                    getOrDefault(cityResponse.country().isoCode()),
                    getOrDefault(cityResponse.city().name()),
                    getOrDefault(cityResponse.postal().code()),
                    getOrDefault(String.valueOf(cityResponse.location().latitude())),
                    getOrDefault(String.valueOf(cityResponse.location().longitude()))
            );
        } catch (IOException e) {
            log.error("Failed to get location for IP: {}", ipAddress, e);
            throw new IOException("Invalid ipAddress or internal error");
        }
    }
}