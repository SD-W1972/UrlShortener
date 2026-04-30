package com.secon.UrlShortener.infrastructure.http;

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;
import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import com.secon.UrlShortener.domain.usecase.AnalyticsProvider;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.OS;
import ua_parser.Parser;
import ua_parser.UserAgent;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class AnalyticsProviderImpl implements AnalyticsProvider {
    private final Parser parser;
    private final DatabaseReader reader;

    public AnalyticsProviderImpl(@Value("${geoip2.database.path}") String databasePath) throws IOException {
        this.parser = new Parser();
        File database = new File(databasePath);
        this.reader = new DatabaseReader.Builder(database).withCache(new CHMCache()).build();
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
        CityResponse cityResponse;

        try {
            cityResponse = reader.city(InetAddress.getByName(ipAddress));
        } catch (IOException ioException) {
            throw new IOException("Invalid ipAddress or internal error");

        }


        return new GeoLocationData(
                getOrDefault(cityResponse.country().isoCode()),
                getOrDefault(cityResponse.city().name()),
                getOrDefault(cityResponse.postal().code()),
                getOrDefault(String.valueOf(cityResponse.location().latitude())),
                getOrDefault(String.valueOf(cityResponse.location().longitude()))
       );

    }
}
