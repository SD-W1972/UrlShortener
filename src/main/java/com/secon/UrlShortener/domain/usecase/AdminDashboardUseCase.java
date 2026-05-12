package com.secon.UrlShortener.domain.usecase;

import com.secon.UrlShortener.domain.model.Click;

import java.util.List;

public interface AdminDashboardUseCase {

    public List<Click> getDataByOriginalUrl(String originalUrl);
    public List<Click> getAllData();

}
