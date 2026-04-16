package com.secon.UrlShortener.outbound.ov;

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
public class JpaClientInfoAdapter {

    private String browser;
    private String browserVersion;
    private String OS;
    private String OSVersion;
    private String device;

}
