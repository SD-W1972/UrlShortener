package com.secon.UrlShortener.domain.model.usecase;

import com.secon.UrlShortener.domain.model.Url;

public interface GetOriginalUrlUseCase {
    Url originalUrl(String slug);
}
