package ru.stqa.pft.soap;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

    @Test(enabled = false)
    public void testMyIp() {
//        GeoIP geoIp = new GeoIPService().getGeoIPServiceSoap12().getGeoIP();
        GeoIP geoIp = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("109.252.26.16");
        assertEquals(geoIp.getCountryCode(), "RUS");
    }
}
