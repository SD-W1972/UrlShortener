package com.secon.UrlShortener.application.port.in.service;

import com.secon.UrlShortener.domain.model.Url;
import com.secon.UrlShortener.domain.model.usecase.GetOriginalUrlUseCase;

public class GetOriginalUrlService implements GetOriginalUrlUseCase {

    @Override
    public Url originalUrl(String slug) {
        return null;
    }
}
