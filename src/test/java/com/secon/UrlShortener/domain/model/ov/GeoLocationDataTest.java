package com.secon.UrlShortener.domain.model.ov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeoLocationDataTest {

    @Test
    public void shouldCreateValidDataTest(){
        GeoLocationData geoLocationData = new GeoLocationData(
            "Brazil",
            "Sao Paulo/SP",
            "01200120",
            "44.9733",
            "-93.2323"
        );

        assertNotNull(geoLocationData);
        assertEquals("Brazil", geoLocationData.getCountry());
        assertEquals("Sao Paulo/SP", geoLocationData.getCity());
        assertEquals("01200120", geoLocationData.getPostal());
        assertEquals("44.9733", geoLocationData.getLatitude());
        assertEquals("-93.2323", geoLocationData.getLongitude());

    }

    @Test
    void shouldThrowExceptionWhenCountryIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new GeoLocationData(null, "Sao Paulo/SP", "01200120", "44.9733", "-93.2323")
        );
    }

    @Test
    void shouldThrowExceptionWhenCountryIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                new GeoLocationData("", "Sao Paulo/SP", "01200120", "44.9733", "-93.2323")
        );
    }

    @Test
    void shouldThrowExceptionWhenCountryIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new GeoLocationData("   ", "Sao Paulo/SP", "01200120", "44.9733", "-93.2323")
        );
    }
}
