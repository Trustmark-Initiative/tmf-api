package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.MediaType;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static edu.gatech.gtri.trustmark.v1_0.io.MediaType.TEXT_HTML;
import static edu.gatech.gtri.trustmark.v1_0.io.MediaType.TEXT_XML;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by brad on 12/15/15.
 */
public class TestSerializerFactoryImpl extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(TestSerializerFactoryImpl.class);

    @Test
    public void testInstantiation() {
        log.info("Making sure we can instantiate SerializerFactory...");
        SerializerFactory factory = FactoryLoader.getInstance(SerializerFactory.class);
        assertThat(factory, notNullValue());
        assertThat(factory, instanceOf(SerializerFactoryImpl.class));
    }

    @Test
    public void testInstantiateJsonSerializer() {
        log.info("Instantiating Json Serializer...");
        Serializer serializer = FactoryLoader.getInstance(SerializerFactory.class).getJsonSerializer();
        assertThat(serializer, notNullValue());
        assertThat(serializer.getOutputMimeFormat(), equalTo(MediaType.APPLICATION_JSON.getMediaType()));
    }

    @Test
    public void testInstantiateXmlSerializer() {
        log.info("Instantiating Xml Serializer...");
        Serializer serializer = FactoryLoader.getInstance(SerializerFactory.class).getXmlSerializer();
        assertThat(serializer, notNullValue());
        assertThat(serializer.getOutputMimeFormat(), equalTo(TEXT_XML.getMediaType()));
    }

    @Test
    public void testInstantiateHtmlSerializer() {
        log.info("Instantiating Html Serializer...");
        Serializer serializer = FactoryLoader.getInstance(SerializerFactory.class).getHtmlSerializer();
        assertThat(serializer, notNullValue());
        assertThat(serializer.getOutputMimeFormat(), equalTo(TEXT_HTML.getMediaType()));
    }


}
