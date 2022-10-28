package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;


import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkDefinitionJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkDefinitionXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.util.ConformanceCriterionUtils;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Created by brad on 1/7/16.
 */
public class TestTrustmarkDefinitionJsonProducer extends AbstractTest {

    public static final String TD_FULL_FILE = "./src/test/resources/TDs/td-full.xml";
    public static final String TD_FULL_JSON_FILE = "./src/test/resources/TDs/td-full.json";

    public static final String TD_FULL_FILE_JSON_CRITERION = "./src/test/resources/TDs/td-full-criterion.json";

    @Test
    public void testJsonOutput() throws Exception {
        logger.info("Testing Simple TrustmarkDefinition JSON Output...");

        logger.debug("Loading trustmark from file...");
        File file = new File(TD_FULL_FILE);
        String text = FileUtils.readFileToString(file);
        TrustmarkDefinition td = new TrustmarkDefinitionXmlDeserializer(true).deserialize(text);
        assertThat(td, notNullValue());
        assertTdFull(td);
        logger.debug("Successfully parsed XML");

        logger.debug("Loading jsonSerializer...");
        Serializer jsonSerializer = FactoryLoader.getInstance(SerializerFactory.class).getJsonSerializer();
        assertThat(jsonSerializer, notNullValue());

        logger.debug("Serializing json...");
        StringWriter jsonWriter = new StringWriter();
        jsonSerializer.serialize(td, jsonWriter);

        String json = jsonWriter.toString();
        assertThat(json, notNullValue());
        logger.debug("Successfully produced json: \n" + json);

        TrustmarkDefinition td2 = new TrustmarkDefinitionJsonDeserializer(true).deserialize(json);
        assertThat(td2, notNullValue());

        assertTdFull(td2);


    }

    @Test
    public void testConformanceCriterionId() throws Exception {
        logger.info("Testing Serialization of a TrustmarkDefinition in JSON emphasizing the conformance criteria id...");

        logger.debug("Loading trustmark from file...");
        File file = new File(TD_FULL_FILE_JSON_CRITERION);
        String text = FileUtils.readFileToString(file);
        TrustmarkDefinition td = new TrustmarkDefinitionJsonDeserializer(true).deserialize(text);
        assertThat(td, notNullValue());
        logger.debug("Successfully parsed JSON");

        logger.debug("Loading jsonSerializer...");
        Serializer jsonSerializer = FactoryLoader.getInstance(SerializerFactory.class).getJsonSerializer();
        assertThat(jsonSerializer, notNullValue());

        logger.debug("Serializing json...");
        StringWriter jsonWriter = new StringWriter();
        jsonSerializer.serialize(td, jsonWriter);

        String json = jsonWriter.toString();
        assertThat(json, notNullValue());
        logger.debug("Successfully produced json: \n" + json);

        TrustmarkDefinition td2 = new TrustmarkDefinitionJsonDeserializer(true).deserialize(json);
        assertThat(td2, notNullValue());

        td2.getConformanceCriteria().forEach(criterion -> {
            assert(criterion.getId().contains(ConformanceCriterionUtils.CRITERION_ID_PREFIX));
        });

    }


}
