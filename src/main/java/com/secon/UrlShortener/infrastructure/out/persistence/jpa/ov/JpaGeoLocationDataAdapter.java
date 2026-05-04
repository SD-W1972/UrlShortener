package com.secon.UrlShortener.infrastructure.out.persistence.jpa.ov;

import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaGeoLocationDataAdapter {

    private String country;
    private String city;
    private String postal;
    private String latitude;
    private String longitude;

    public JpaGeoLocationDataAdapter(GeoLocationData geoLocationData){
        this.country = geoLocationData.getCountry();
        this.city = geoLocationData.getCity();
        this.postal = geoLocationData.getPostal();
        this.latitude = geoLocationData.getLatitude();
        this.longitude = geoLocationData.getLongitude();
    }
}
