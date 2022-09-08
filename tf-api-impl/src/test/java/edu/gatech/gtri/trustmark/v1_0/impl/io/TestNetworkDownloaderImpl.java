package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.HttpResponse;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by brad on 12/8/15.
 */
public class TestNetworkDownloaderImpl extends AbstractTest {

    @Test
    public void testDefaultResolution() {
        NetworkDownloader downloader = FactoryLoader.getInstance(NetworkDownloader.class);
        assertThat(downloader, notNullValue());
        assertThat(downloader, instanceOf(NetworkDownloaderImpl.class));
    }


    @Test
    public void testGetSimpleHTMLResource() throws Exception {
        logger.info("Testing that we can get example.org's home page...");
        NetworkDownloaderImpl downloader = new NetworkDownloaderImpl();
        HttpResponse response = downloader.download(new URL("http://example.org/"));
        assertThat(response, notNullValue());
        String content = response.getContent();
        logger.debug("Downloaded content: \n"+content);
        assertThat(content, notNullValue());
        assertThat(content, containsString("<h1>Example Domain</h1>"));
        logger.info("Successfully downloaded simple HTML resource");
    }//end testGet()


    @Test
    public void testGetBinaryHTMLResource() throws Exception {
        logger.info("Testing that we can get a binary object without flaking out...");
        NetworkDownloaderImpl downloader = new NetworkDownloaderImpl();
        HttpResponse response = downloader.download(new URL("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"));
        assertThat(response, notNullValue());
        assertThat(response.getContentType(), equalTo("image/png"));
        assertThat(response.getBinaryContent(), notNullValue());
        assertThat(response.getBinaryContent().length, equalTo(13504));
    }//end testGetBinaryHTMLResource()



}//end testGetSimpleHTMLResource()
