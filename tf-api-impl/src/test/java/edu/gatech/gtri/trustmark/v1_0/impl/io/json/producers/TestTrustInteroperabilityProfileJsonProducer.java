package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;


import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustInteroperabilityProfileJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustInteroperabilityProfileXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Created by brad on 1/7/16.
 */
public class TestTrustInteroperabilityProfileJsonProducer extends AbstractTest {

    public static final String TIP_FULL_FILE = "./src/test/resources/TIPs/tip-full.xml";

    @Test
    public void testJsonOutput() throws Exception {
        logger.info("Testing Simple TIP JSON Output...");

        logger.debug("Loading TIP from file...");
        File file = new File(TIP_FULL_FILE);
        String text = FileUtils.readFileToString(file);
        TrustInteroperabilityProfile tip = new TrustInteroperabilityProfileXmlDeserializer().deserialize(text);
        assertTipFull(tip);
        assertThat(tip, notNullValue());

        Serializer jsonSerializer = FactoryLoader.getInstance(SerializerFactory.class).getJsonSerializer();
        assertThat(jsonSerializer, notNullValue());

        StringWriter jsonWriter = new StringWriter();
        jsonSerializer.serialize(tip, jsonWriter);

        String json = jsonWriter.toString();
        assertThat(json, notNullValue());

        logger.debug("Successfully produced json: \n" + json);

        TrustInteroperabilityProfile tip2 = new TrustInteroperabilityProfileJsonDeserializer().deserialize(json);
        assertThat(tip2, notNullValue());
        assertTipFull(tip2);

    }


}
