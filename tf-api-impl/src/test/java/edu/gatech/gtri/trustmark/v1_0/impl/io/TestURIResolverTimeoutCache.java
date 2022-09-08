package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.HttpResponse;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static edu.gatech.gtri.trustmark.v1_0.io.MediaType.TEXT_PLAIN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by brad on 12/8/15.
 */
public class TestURIResolverTimeoutCache extends AbstractTest {

    @BeforeEach
    public void registerNetworkDownloader() {
        FactoryLoader.register(NetworkDownloader.class, new NetworkDownloaderFaker());
    }

    @AfterEach
    public void unregisterNetworkDownloader() {
        FactoryLoader.unregister(NetworkDownloader.class);
    }


    @Test
    public void testTimeout() throws Exception {
        String nowTestURL = "http://gtri.org/#now-test";
        long timeout = 50l;

        logger.info("Testing the cache until timeout works...");
        URIResolverTimeoutCache uriResolverSimple = new URIResolverTimeoutCache(timeout);
        String content1 = uriResolverSimple.resolve(nowTestURL);
        assertThat(content1, notNullValue());
        try {
            Thread.sleep(10l);
        } catch (Exception e) {
        }
        // This call will happen after the first one, and should have been enough time for the page to change.
        String content2 = uriResolverSimple.resolve(nowTestURL);
        assertThat(content2, notNullValue());

        // The content should be equal, because the cache window is still valid.
        assertThat(content1, equalTo(content2));

        try {
            Thread.sleep(timeout + 10l);
        } catch (Throwable t) {
        } // Guaranteed to wait longer than cache expiration.

        // The cache window has expired, so the page should be different now.
        String content3 = uriResolverSimple.resolve(nowTestURL);
        assertThat(content3, notNullValue());

        // The content should be equal, because the cache window is still valid.
        assertThat(content3, not(equalTo(content2)));


    }//end testGet()


    public static class NetworkDownloaderFaker implements NetworkDownloader {
        @Override
        public HttpResponse download(URL url) throws IOException {
            if (url.toExternalForm().contains("now-test")) {
                return new HttpResponse() {
                    @Override
                    public List<String> getHeader(String name) {
                        return null;
                    }

                    @Override
                    public Map<String, List<String>> getHeaders() {
                        return null;
                    }

                    @Override
                    public int getResponseCode() {
                        return 200;
                    }

                    @Override
                    public String getResponseMessage() {
                        return null;
                    }

                    @Override
                    public String getContentType() {
                        return TEXT_PLAIN.getMediaType();
                    }

                    @Override
                    public boolean isBinary() {
                        return false;
                    }

                    @Override
                    public String getContent() {
                        return System.currentTimeMillis() + "";
                    }

                    @Override
                    public byte[] getBinaryContent() {
                        return (System.currentTimeMillis() + "").getBytes(Charset.defaultCharset());
                    }
                };
            } else {
                throw new IOException("CANNOT DOWNLOAD");
            }
        }
    }


}//end testGetSimpleHTMLResource()
