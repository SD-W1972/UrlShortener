package com.secon.UrlShortener.domain.usecase;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;

import java.io.IOException;
import java.net.UnknownHostException;

public interface AnalyticsProvider {
    ClientInfo getClientInfo(String userAgent);
    GeoLocationData getGeoLocationData(String ipAddress) throws UnknownHostException, IOException, GeoIp2Exception;
}
