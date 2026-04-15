package com.secon.UrlShortener.domain.model.ov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientInfoTest {

    @Test
    public void shouldCreateValidClientInfo(){
        ClientInfo clientInfo = new ClientInfo(
                "Chrome",
                "149.0.2",
                "Ubuntu x86_64",
                "24.0.2",
                "20DSS27P00 (ThinkPad L450)"
        );

        assertNotNull(clientInfo);
        assertEquals("Chrome", clientInfo.getBrowser());
        assertEquals("149.0.2", clientInfo.getBrowserVersion());
        assertEquals("Ubuntu x86_64", clientInfo.getOS());
        assertEquals("24.0.2", clientInfo.getOSVersion());
        assertEquals("20DSS27P00 (ThinkPad L450)", clientInfo.getDevice());
    }

    @Test
    public void shouldThrowAnExceptionWhenOSIsNull(){
        String OS = null;
        assertThrows(IllegalArgumentException.class, () -> new ClientInfo(
                "Chrome",
                "149.0.2",
                OS,
                "24.0.2",
                "20DSS27P00 (ThinkPad L450)"

        ));
    }
}
