package com.secon.UrlShortener.infrastructure.adapter.out.persistence.ov;

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

}
