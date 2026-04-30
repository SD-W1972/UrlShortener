package com.secon.UrlShortener.domain.model.ov;

import java.util.Objects;

public class ClientInfo {
    private String browser;
    private String browserVersion;
    private String OS;
    private String OSVersion;
    private String device;

    public ClientInfo(String browser, String browserVersion, String OS, String OSVersion, String device) {
        this.browser = browser;
        this.browserVersion = browserVersion;
        validate(OS);
        this.OS = OS;
        this.OSVersion = OSVersion;
        this.device = device;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getOSVersion() {
        return OSVersion;
    }

    public void setOSVersion(String OSVersion) {
        this.OSVersion = OSVersion;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void validate(String OS){
        if(OS == null|| OS.isBlank())
            throw new IllegalArgumentException("OS can't be null or empty");
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "browser='" + browser + '\'' +
                ", browserVersion='" + browserVersion + '\'' +
                ", OS='" + OS + '\'' +
                ", OSVersion='" + OSVersion + '\'' +
                ", device='" + device + '\'' +
                '}';
    }

    public static ClientInfo unknown() {
        return new ClientInfo("unknown", "unknown", "unknown", "unknown", "unknown");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientInfo that = (ClientInfo) o;
        return Objects.equals(browser, that.browser) && Objects.equals(browserVersion, that.browserVersion) && Objects.equals(OS, that.OS) && Objects.equals(OSVersion, that.OSVersion) && Objects.equals(device, that.device);
    }

    @Override
    public int hashCode() {
        return Objects.hash(browser, browserVersion, OS, OSVersion, device);
    }
}
