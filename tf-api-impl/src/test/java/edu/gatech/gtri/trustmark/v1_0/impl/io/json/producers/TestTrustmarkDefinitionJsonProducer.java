package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;


import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkDefinitionJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkDefinitionXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.util.TrustmarkDefinitionUtils;
import edu.gatech.gtri.trustmark.v1_0.util.diff.TrustmarkDefinitionDiffResult;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


/**
 * Created by brad on 1/7/16.
 */
public class TestTrustmarkDefinitionJsonProducer extends AbstractTest {

    public static final String TD_FULL_FILE = "./src/test/resources/TDs/td-full.xml";
    public static final String TD_FULL_JSON_FILE = "./src/test/resources/TDs/td-full.json";

    @Test
    public void testJsonOutput() throws Exception {
        logger.info("Testing Simple TrustmarkDefinition JSON Output...");

        logger.debug("Loading trustmark from file...");
        File file = new File(TD_FULL_FILE);
        String text = FileUtils.readFileToString(file);
        TrustmarkDefinition td = TrustmarkDefinitionXmlDeserializer.deserialize(text);
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
        logger.debug("Successfully produced json: \n"+json);

        TrustmarkDefinition td2 = TrustmarkDefinitionJsonDeserializer.deserialize(json);
        assertThat(td2, notNullValue());

        assertTdFull(td2);


    }



}
