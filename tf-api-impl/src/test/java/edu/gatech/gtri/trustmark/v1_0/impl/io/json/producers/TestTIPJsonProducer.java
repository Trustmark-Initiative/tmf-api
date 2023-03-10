package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;


import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustInteroperabilityProfileJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustInteroperabilityProfileXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


/**
 * XML -> TrustInteroperabilityProfile -> JSON
 */
public class TestTIPJsonProducer extends AbstractTest {
    private static final Logger logger = LoggerFactory.getLogger(TestTIPJsonProducer.class);

    public static final String TIP_FULL_FILE = "./src/test/resources/TIPs/tip-full.xml";

    @Test
    public void testJsonOutput() throws Exception {
        logger.info("Testing Simple TIP JSON Output...");

        logger.debug("Loading TIP from file... " + TIP_FULL_FILE);
        File file = new File(TIP_FULL_FILE);
        String text = FileUtils.readFileToString(file);
        TrustInteroperabilityProfile tip = new TrustInteroperabilityProfileXmlDeserializer(true).deserialize(text);
        assertTipFull(tip);
        assertThat(tip, notNullValue());

        Serializer jsonSerializer = FactoryLoader.getInstance(SerializerFactory.class).getJsonSerializer();
        assertThat(jsonSerializer, notNullValue());

        StringWriter jsonWriter = new StringWriter();
        jsonSerializer.serialize(tip, jsonWriter);

        String json = jsonWriter.toString();
        assertThat(json, notNullValue());

        logger.debug("Successfully produced json: \n" + json);

        TrustInteroperabilityProfile tip2 = new TrustInteroperabilityProfileJsonDeserializer(true).deserialize(json);
        assertThat(tip2, notNullValue());
        assertTipFull(tip2);

    }


}
