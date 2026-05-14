package com.secon.UrlShortener.application.service;

import com.secon.UrlShortener.domain.model.Click;
import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import com.secon.UrlShortener.domain.out.ClickRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AdminDashboardServiceTest {

    @Mock
    private ClickRepository clickRepository;

    @InjectMocks
    private AdminDashboardService adminDashboardService;

    private List<Click> expectedClicks = new ArrayList<>();
    private Click defaultClick;
    private Click clickDifferentUrl;

    @BeforeEach
    public void setup(){
        String originalUrl = "https://google.com";
        String slug = "abc123";
        LocalDateTime clickedAt = LocalDateTime.now();
        GeoLocationData geoLocationData = new GeoLocationData(
                "Brazil",
                "Sao Paulo",
                "2222222",
                "lattitude",
                "longitude"
        );
        ClientInfo clientInfo = new ClientInfo(
                "Chrome",
                "1.1.1",
                "Windows",
                "10",
                "computer"
        );

        defaultClick = new Click(
                originalUrl,
                slug,
                clickedAt,
                clientInfo,
                geoLocationData,
                "111.111.111");

        clickDifferentUrl = new Click(
                "https://youtube.com",
                slug,
                clickedAt,
                clientInfo,
                geoLocationData,
                "111.111.111");


        expectedClicks.add(defaultClick);
        expectedClicks.add(defaultClick);
        expectedClicks.add(defaultClick);

    }

    @Test
    public void shouldReturnAllData(){
        when(clickRepository.findAll()).thenReturn(expectedClicks);

        List<Click> returnedClicks = adminDashboardService.getAllData();
        assertNotNull(returnedClicks);
        assertEquals(expectedClicks, returnedClicks);
    }

    @Test
    public void shouldReturnOnlyClicksWithTheSameOriginalUrl(){
        List<Click> clicksWithDifferenteUrl = List.of(clickDifferentUrl);

        when(clickRepository.findAllByOriginalUrl("https://youtube.com")).thenReturn(clicksWithDifferenteUrl);

        List<Click> returnedClicks = adminDashboardService.getDataByOriginalUrl("https://youtube.com");

        assertNotNull(returnedClicks);
        assertEquals(clicksWithDifferenteUrl, returnedClicks);
    }
}
