package edu.gatech.gtri.trustmark.v1_0.io;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestMediaType {

    @Test
    public void testGetMediaType() {
        Assertions.assertEquals(MediaType.APPLICATION_JSON.getMediaType(), "application/json");
        Assertions.assertEquals(MediaType.APPLICATION_PDF.getMediaType(), "application/pdf");
        Assertions.assertEquals(MediaType.APPLICATION_XML.getMediaType(), "application/xml");
        Assertions.assertEquals(MediaType.APPLICATION_ZIP.getMediaType(), "application/zip");
        Assertions.assertEquals(MediaType.TEXT_HTML.getMediaType(), "text/html");
        Assertions.assertEquals(MediaType.TEXT_PLAIN.getMediaType(), "text/plain");
        Assertions.assertEquals(MediaType.TEXT_XML.getMediaType(), "text/xml");
    }
}
