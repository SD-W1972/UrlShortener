package com.secon.UrlShortener.domain.usecase;

import jakarta.servlet.http.HttpServletRequest;

public interface AnalyticsUseCase {
    String country();
    String city();
    String postal();
    Double latitude();
    Double longitude();

    String browser(HttpServletRequest request);
    String browserVersion(HttpServletRequest request);
    String OS(HttpServletRequest request);
    String OSVersion(HttpServletRequest request);
    String deivce(HttpServletRequest request);
}
