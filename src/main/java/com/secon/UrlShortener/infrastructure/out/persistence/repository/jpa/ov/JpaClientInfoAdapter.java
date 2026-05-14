package com.secon.UrlShortener.infrastructure.out.persistence.repository.jpa.ov;

import com.secon.UrlShortener.domain.model.ov.ClientInfo;
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

    public JpaClientInfoAdapter(ClientInfo clientInfo){
        this.browser = clientInfo.getBrowser();
        this.browserVersion = clientInfo.getBrowserVersion();
        this.OS = clientInfo.getOS();
        this.OSVersion = clientInfo.getOSVersion();
        this.device = clientInfo.getDevice();
    }
}
