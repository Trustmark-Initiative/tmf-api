package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.impl.io.json.producers.TrustmarkBindingRegistryOrganizationMapJsonProducer;
import edu.gatech.gtri.trustmark.v1_0.model.trustmarkBindingRegistry.TrustmarkBindingRegistryOrganizationMap;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestTrustmarkBindingRegistryOrganizationMapJsonDeserializer extends AbstractTest {

    // TODO: The JSON file referenced in this test contains URLs that seem no longer available causing the test to fail.
    //       Restore the test by either updating to valid URLs or redesigning the test with fakes to avoid using harcoded URLs.

//    @Test
//    public void test() throws Exception {
//        File file = new File("./src/test/resources/edu/gatech/gtri/trustmark/v1_0/impl/io/json/organizations.json");
//        String json = FileUtils.readFileToString(file);
//        TrustmarkBindingRegistryOrganizationMap trustmarkBindingRegistryOrganizationMap1 = new TrustmarkBindingRegistryOrganizationMapJsonDeserializer().deserialize(json);
//
//        trustmarkBindingRegistryOrganizationMap1.getOrganizationMap().forEach(trustmarkBindingRegistryOrganization -> {
//            logger.info("getIdentifier: " + trustmarkBindingRegistryOrganization._2().getIdentifier());
//            logger.info("getName: " + trustmarkBindingRegistryOrganization._2().getName());
//            logger.info("getDisplayName: " + trustmarkBindingRegistryOrganization._2().getDisplayName());
//            logger.info("getDescription: " + trustmarkBindingRegistryOrganization._2().getDescription());
//            trustmarkBindingRegistryOrganization._2().getOrganizationTrustmarkMap().forEach(trustmarkBindingRegistryOrganizationTrustmark -> {
//                logger.info("getComment: " + trustmarkBindingRegistryOrganizationTrustmark._2().getComment());
//                logger.info("getName: " + trustmarkBindingRegistryOrganizationTrustmark._2().getName());
//                logger.info("getStatus: " + trustmarkBindingRegistryOrganizationTrustmark._2().getStatus());
//                logger.info("getTrustmarkDefinitionIdentifier: " + trustmarkBindingRegistryOrganizationTrustmark._2().getTrustmarkDefinitionIdentifier());
//                logger.info("getTrustmarkIdentifier: " + trustmarkBindingRegistryOrganizationTrustmark._2().getTrustmarkIdentifier());
//                logger.info("isProvisional: " + trustmarkBindingRegistryOrganizationTrustmark._2().isProvisional());
//            });
//        });
//
//        JSONObject jsonObject = new TrustmarkBindingRegistryOrganizationMapJsonProducer().serialize(trustmarkBindingRegistryOrganizationMap1);
//
//        TrustmarkBindingRegistryOrganizationMap trustmarkBindingRegistryOrganizationMap2 = new TrustmarkBindingRegistryOrganizationMapJsonDeserializer().deserialize(jsonObject.toString());
//
//        trustmarkBindingRegistryOrganizationMap2.getOrganizationMap().forEach(trustmarkBindingRegistryOrganization -> {
//            logger.info("getIdentifier: " + trustmarkBindingRegistryOrganization._2().getIdentifier());
//            logger.info("getName: " + trustmarkBindingRegistryOrganization._2().getName());
//            logger.info("getDisplayName: " + trustmarkBindingRegistryOrganization._2().getDisplayName());
//            logger.info("getDescription: " + trustmarkBindingRegistryOrganization._2().getDescription());
//            trustmarkBindingRegistryOrganization._2().getOrganizationTrustmarkMap().forEach(trustmarkBindingRegistryOrganizationTrustmark -> {
//                logger.info("getComment: " + trustmarkBindingRegistryOrganizationTrustmark._2().getComment());
//                logger.info("getName: " + trustmarkBindingRegistryOrganizationTrustmark._2().getName());
//                logger.info("getStatus: " + trustmarkBindingRegistryOrganizationTrustmark._2().getStatus());
//                logger.info("getTrustmarkDefinitionIdentifier: " + trustmarkBindingRegistryOrganizationTrustmark._2().getTrustmarkDefinitionIdentifier());
//                logger.info("getTrustmarkIdentifier: " + trustmarkBindingRegistryOrganizationTrustmark._2().getTrustmarkIdentifier());
//                logger.info("isProvisional: " + trustmarkBindingRegistryOrganizationTrustmark._2().isProvisional());
//            });
//        });
//    }
}
