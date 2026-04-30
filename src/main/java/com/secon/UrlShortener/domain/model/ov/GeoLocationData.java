package com.secon.UrlShortener.domain.model.ov;

import java.util.Objects;

public class GeoLocationData {

    private String country;
    private String city;
    private String postal;
    private String latitude;
    private String longitude;

    public GeoLocationData(String country, String city, String postal, String latitude, String longitude) {
        validate(country);
        this.country = country;
        this.city = city;
        this.postal = postal;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    private void validate(String country) {
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country can't be null or empty");
        }
    }

    @Override
    public String toString() {
        return "GeoLocationData{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", postal='" + postal + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GeoLocationData that = (GeoLocationData) o;
        return Objects.equals(country, that.country) && Objects.equals(city, that.city) && Objects.equals(postal, that.postal) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, postal, latitude, longitude);
    }

    public static GeoLocationData unknown() {
        return new GeoLocationData("unknown", "unknown", "unknown", "unknown", "unknown");
    }
}