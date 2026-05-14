package com.secon.UrlShortener.application.service;

import com.secon.UrlShortener.domain.model.Click;
import com.secon.UrlShortener.domain.out.ClickRepository;
import com.secon.UrlShortener.domain.usecase.AdminDashboardUseCase;

import java.util.List;

public class AdminDashboardService implements AdminDashboardUseCase {

    private final ClickRepository clickRepository;

    public AdminDashboardService(ClickRepository clickRepository) {
        this.clickRepository = clickRepository;
    }

    @Override
    public List<Click> getDataByOriginalUrl(String originalUrl) {
        return List.of();
    }

    @Override
    public List<Click> getAllData() {
        return clickRepository.findAll();
    }
}
