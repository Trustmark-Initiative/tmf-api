package edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers;


import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.TrustmarkStatusReportJsonDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkStatusReportXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.TrustmarkXmlDeserializer;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Created by brad on 1/7/16.
 */
public class TestTrustmarkStatusReportJsonProducer extends AbstractTest {

    public static final String TSR_FULL_FILE = "./src/test/resources/TSRs/statusreport-full.xml";

    @Test
    public void testJsonOutput() throws Exception {
        logger.info("Testing Simple Trustmark Status Report JSON Output...");

        logger.debug("Loading Trustmark Status Report from file...");
        File file = new File(TSR_FULL_FILE);
        String text = FileUtils.readFileToString(file);
        TrustmarkStatusReport tsr = TrustmarkStatusReportXmlDeserializer.deserialize(text);
        assertThat(tsr, notNullValue());

        Serializer jsonSerializer = FactoryLoader.getInstance(SerializerFactory.class).getJsonSerializer();
        assertThat(jsonSerializer, notNullValue());

        StringWriter jsonWriter = new StringWriter();
        jsonSerializer.serialize(tsr, jsonWriter);

        String json = jsonWriter.toString();
        assertThat(json, notNullValue());

        logger.debug("Successfully produced json: \n"+json);

        TrustmarkStatusReport tsr2 = TrustmarkStatusReportJsonDeserializer.deserialize(json);
        assertThat(tsr2, notNullValue());

        // TODO Other assertions...

    }



}
