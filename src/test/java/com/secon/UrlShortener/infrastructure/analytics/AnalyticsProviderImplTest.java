package com.secon.UrlShortener.infrastructure.analytics;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Postal;
import com.secon.UrlShortener.domain.model.ov.ClientInfo;
import com.secon.UrlShortener.domain.model.ov.GeoLocationData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua_parser.Client;
import ua_parser.Parser;

import java.io.IOException;
import java.net.InetAddress;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AnalyticsProviderImplTest {

    private AnalyticsProviderImpl analyticsProvider;
    private String uaString;
    private String ipAddress;
    private Client c;

    @BeforeEach
    public void setup() throws Exception {
        uaString = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";
        ipAddress = "128.101.101.101";

        Parser parser = new Parser();
        c = parser.parse(uaString);

        DatabaseReader mockReader = mock(DatabaseReader.class);
        CityResponse mockResponse = mock(CityResponse.class);

        Country mockCountry = mock(Country.class);
        City mockCity = mock(City.class);
        Postal mockPostal = mock(Postal.class);
        Location mockLocation = mock(Location.class);

        when(mockReader.city(InetAddress.getByName(ipAddress))).thenReturn(mockResponse);

        when(mockResponse.country()).thenReturn(mockCountry);
        when(mockCountry.isoCode()).thenReturn("US");

        when(mockResponse.city()).thenReturn(mockCity);
        when(mockCity.name()).thenReturn("Minneapolis");

        when(mockResponse.postal()).thenReturn(mockPostal);
        when(mockPostal.code()).thenReturn("55455");

        when(mockResponse.location()).thenReturn(mockLocation);
        when(mockLocation.latitude()).thenReturn(55.9733);
        when(mockLocation.longitude()).thenReturn(-93.2323);

        analyticsProvider = new AnalyticsProviderImpl(parser, mockReader);
    }
    @Test
    public void shouldGetBrowserVersionIfUserAgentIsValid(){
        String hardcodedBrowserVersion = c.userAgent.major;
        hardcodedBrowserVersion += "." + c.userAgent.minor;

        String version = analyticsProvider.getBrowserVersion(c.userAgent);

        Assertions.assertNotNull(version);
        Assertions.assertEquals(hardcodedBrowserVersion, version);
    }

    @Test
    public void shouldReturnUnknowBrowserVersionIfUserAgentIsNull(){
        Assertions.assertNotNull(analyticsProvider.getOsVersion(null));
        Assertions.assertEquals("unknown", analyticsProvider.getBrowserVersion(null));
    }

    @Test
    public void shouldReturnOSVersionIfOSIsValid(){
        String hardcodedOSVersion = c.os.major;
        hardcodedOSVersion += "." + c.os.minor;
        hardcodedOSVersion += "." + c.os.patch;

        String version = analyticsProvider.getOsVersion(c.os);

        Assertions.assertNotNull(version);
        Assertions.assertEquals(hardcodedOSVersion, version);
    }

    @Test
    public void shouldReturnUnknowIfOSIsNullOrEmpty(){
        Assertions.assertNotNull(analyticsProvider.getOsVersion(null));
        Assertions.assertEquals("unknown", analyticsProvider.getOsVersion(null));
    }

    @Test
    public void shouldReturnUnknowIfValueIsNull(){
        Assertions.assertEquals("unknown", analyticsProvider.getOrDefault(null));
        Assertions.assertEquals("unknown", analyticsProvider.getOrDefault(""));
    }

    @Test
    public void shouldReturnItSelfIfValueIsValid(){
        Assertions.assertEquals("Safari", analyticsProvider.getOrDefault("Safari"));
    }

    @Test
    public void shouldReturnValidClientInfoObjIfEverythingIsAlright(){
        ClientInfo hardcodedClientInfo = new ClientInfo(
                "Mobile Safari",
                "5.1",
                "iOS",
                "5.1.1",
                "iPhone"
        );


        ClientInfo clientInfo = analyticsProvider.getClientInfo(uaString);

        Assertions.assertNotNull(clientInfo);
        Assertions.assertEquals(hardcodedClientInfo, clientInfo);
    }

    @Test
    public void shouldReturnUnknowClientInfoObjIfUserAgentIsNullOrBlank(){
        ClientInfo hardcodedClientInfo = new ClientInfo(
                "unknown",
                "unknown",
                "unknown",
                "unknown",
                "unknown"
                );
        Assertions.assertNotNull(analyticsProvider.getClientInfo(null));
        Assertions.assertNotNull(analyticsProvider.getClientInfo(""));
        Assertions.assertEquals(hardcodedClientInfo, analyticsProvider.getClientInfo(null));
        Assertions.assertEquals(hardcodedClientInfo, analyticsProvider.getClientInfo(""));

    }

    @Test
    public void shouldReturnValidGeoLocationDataObjIfEverythingIsAlright() throws IOException, GeoIp2Exception {

        GeoLocationData hardcodedGeoLocationData = new GeoLocationData(
                "US",
                "Minneapolis",
                "55455",
                "55.9733",
                "-93.2323"
        );

        GeoLocationData geoLocationData = analyticsProvider.getGeoLocationData(ipAddress);

        Assertions.assertNotNull(geoLocationData);
        Assertions.assertEquals(hardcodedGeoLocationData, geoLocationData);
    }


}
