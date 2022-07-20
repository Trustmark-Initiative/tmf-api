package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationMapJsonProducer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistrySystemMapJsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistrySystemMap;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestTrustmarkBindingRegistrySystemMapJsonDeserializer extends AbstractTest {

    @Test
    public void test() throws Exception {
        testHelper("./src/test/resources/edu/gatech/gtri/trustmark/v1_0/impl/io/json/empty.json");
        testHelper("./src/test/resources/edu/gatech/gtri/trustmark/v1_0/impl/io/json/OIDC_RP.json");
        testHelper("./src/test/resources/edu/gatech/gtri/trustmark/v1_0/impl/io/json/SAML_SP.json");
        testHelper("./src/test/resources/edu/gatech/gtri/trustmark/v1_0/impl/io/json/SAML_IDP.json");
        testHelper("./src/test/resources/edu/gatech/gtri/trustmark/v1_0/impl/io/json/certificate_1.json");
        testHelper("./src/test/resources/edu/gatech/gtri/trustmark/v1_0/impl/io/json/certificate_2.json");
        testHelper("./src/test/resources/edu/gatech/gtri/trustmark/v1_0/impl/io/json/OIDC_OP_1.json");
//        testHelper("./src/test/resources/edu/gatech/gtri/trustmark/v1_0/impl/io/json/OIDC_OP_2.json");
    }

    public void testHelper(final String fileName) throws Exception {
        File file = new File(fileName);
        String json = FileUtils.readFileToString(file);
        TrustmarkBindingRegistrySystemMap trustmarkBindingRegistrySystemMap1 = new TrustmarkBindingRegistrySystemMapJsonDeserializer().deserialize(json);

        trustmarkBindingRegistrySystemMap1.getSystemMap().forEach(trustmarkBindingRegistrySystem -> {
            logger.info("getIdentifier: " + trustmarkBindingRegistrySystem._2().getIdentifier());
            logger.info("getName: " + trustmarkBindingRegistrySystem._2().getName());
            logger.info("getDisplayName: " + trustmarkBindingRegistrySystem._2().getSystemType());
            logger.info("getDescription: " + trustmarkBindingRegistrySystem._2().getMetadata());

            trustmarkBindingRegistrySystem._2().getTrustmarkRecipientIdentifiers().forEach(uri -> {
                logger.info("getTrustmarkRecipientIdentifiers: " + uri.toString());
            });

            trustmarkBindingRegistrySystem._2().getTrustmarks().forEach(uri -> {
                logger.info("getTrustmarks: " + uri.toString());
            });
        });

        JSONArray jsonArray = new TrustmarkBindingRegistrySystemMapJsonProducer().serialize(trustmarkBindingRegistrySystemMap1);

        TrustmarkBindingRegistrySystemMap trustmarkBindingRegistrySystemMap2 = new TrustmarkBindingRegistrySystemMapJsonDeserializer().deserialize(jsonArray.toString());

        trustmarkBindingRegistrySystemMap2.getSystemMap().forEach(trustmarkBindingRegistrySystem -> {
            logger.info("getIdentifier: " + trustmarkBindingRegistrySystem._2().getIdentifier());
            logger.info("getName: " + trustmarkBindingRegistrySystem._2().getName());
            logger.info("getDisplayName: " + trustmarkBindingRegistrySystem._2().getSystemType());
            logger.info("getDescription: " + trustmarkBindingRegistrySystem._2().getMetadata());

            trustmarkBindingRegistrySystem._2().getTrustmarkRecipientIdentifiers().forEach(uri -> {
                logger.info("getTrustmarkRecipientIdentifiers: " + uri.toString());
            });

            trustmarkBindingRegistrySystem._2().getTrustmarks().forEach(uri -> {
                logger.info("getTrustmarks: " + uri.toString());
            });
        });

    }
}
