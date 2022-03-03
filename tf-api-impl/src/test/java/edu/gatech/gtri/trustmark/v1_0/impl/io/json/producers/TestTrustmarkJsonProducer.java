package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;


import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Created by brad on 1/7/16.
 */
public class TestTrustmarkJsonProducer extends AbstractTest {

    public static final String TRUSTMARK_FULL_FILE = "./src/test/resources/Trustmarks/trustmark-full.xml";

    @Test
    public void testJsonOutput() throws Exception {
        logger.info("Testing Simple Trustmark JSON Output...");

        logger.debug("Loading trustmark from file...");
        File file = new File(TRUSTMARK_FULL_FILE);
        String text = FileUtils.readFileToString(file);
        Trustmark trustmark = new TrustmarkXmlDeserializer().deserialize(text);
        assertThat(trustmark, notNullValue());

        Serializer jsonSerializer = FactoryLoader.getInstance(SerializerFactory.class).getJsonSerializer();
        assertThat(jsonSerializer, notNullValue());

        StringWriter jsonWriter = new StringWriter();
        jsonSerializer.serialize(trustmark, jsonWriter);

        String json = jsonWriter.toString();
        assertThat(json, notNullValue());

        logger.debug("Successfully produced json: \n" + json);

        Trustmark tm2 = new TrustmarkJsonDeserializer().deserialize(json);
        assertThat(tm2, notNullValue());

        // TODO Other assertions...

    }


}
