package com.secon.UrlShortener.application.utilities;

import com.secon.UrlShortener.domain.model.Click;
import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ToStringListTest {
    private List<String> expectedList;
    private List<Click> providedCLickList;

    @BeforeEach
    public void setup(){
        expectedList = new ArrayList<>();
        providedCLickList = new ArrayList<>();

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

        Click defaultClick = new Click(
                originalUrl,
                slug,
                clickedAt,
                clientInfo,
                geoLocationData,
                "111.111.111");


        providedCLickList.add(defaultClick);
        providedCLickList.add(defaultClick);
        providedCLickList.add(defaultClick);

        expectedList.add(defaultClick.toString());
        expectedList.add(defaultClick.toString());
        expectedList.add(defaultClick.toString());

    }

    @Test
    public void shouldConvertFromClickListToStringList(){
        List<String> actualList = ToStringList.toStringList(providedCLickList);

        assertNotNull(actualList);
        assertEquals(expectedList, actualList);

    }
}
