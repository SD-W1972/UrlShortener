package com.secon.UrlShortener.domain.model.ov;

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

    public void validate(String country){
        if(country.isBlank() || country.isEmpty() || country == null){
            throw new IllegalArgumentException("Country can't be null or empty");
        }
    }
}
