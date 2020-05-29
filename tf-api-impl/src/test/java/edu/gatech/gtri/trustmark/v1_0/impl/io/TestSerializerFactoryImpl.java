package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by brad on 12/15/15.
 */
public class TestSerializerFactoryImpl extends AbstractTest {

    private static final Logger log = Logger.getLogger(TestSerializerFactoryImpl.class);

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
        assertThat(serializer.getOutputMimeFormat(), equalTo("application/json"));
    }

    @Test
    public void testInstantiateXmlSerializer() {
        log.info("Instantiating Xml Serializer...");
        Serializer serializer = FactoryLoader.getInstance(SerializerFactory.class).getXmlSerializer();
        assertThat(serializer, notNullValue());
        assertThat(serializer.getOutputMimeFormat(), equalTo("text/xml"));
    }

    @Test
    public void testInstantiateHtmlSerializer() {
        log.info("Instantiating Html Serializer...");
        Serializer serializer = FactoryLoader.getInstance(SerializerFactory.class).getHtmlSerializer();
        assertThat(serializer, notNullValue());
        assertThat(serializer.getOutputMimeFormat(), equalTo("text/html"));
    }


}
