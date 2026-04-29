package com.secon.UrlShortener.infrastructure.http;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.WebServiceClient;
import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import com.secon.UrlShortener.domain.usecase.AnalyticsProvider;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import ua_parser.Client;
import ua_parser.OS;
import ua_parser.Parser;
import ua_parser.UserAgent;

import java.io.File;
import java.io.IOException;

public class AnalyticsProviderImpl implements AnalyticsProvider {

    private  Parser parser;
    private File database;
    private DatabaseReader reader;

    @Value("${geoip2.database.path}")
    String databasePath;

    public AnalyticsProviderImpl() {
    }

    @PostConstruct
    public void init() throws IOException {
        this.parser = new Parser();
        this.database = new File(databasePath);

        try {
           this.reader = new DatabaseReader.Builder(database).build();
        }catch(IOException ioException){
           throw new IOException("Error");
        }
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

    private String getBrowserVersion(UserAgent ua) {
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

    private String getOsVersion(OS os) {
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

    private String getOrDefault(String value) {
        return value != null && !value.isEmpty() && !value.equals("null") ? value : "unknown";
    }
    @Override
    public GeoLocationData getGeoLocationData(String ipAddress) {

        return null;
    }
}
