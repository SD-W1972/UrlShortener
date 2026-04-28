package com.secon.UrlShortener.domain.usecase;

import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;

public interface AnalyticsProvider {
    ClientInfo getClientInfo(String userAgent);
    GeoLocationData getGeoLocationData(String ipAddress);
}
