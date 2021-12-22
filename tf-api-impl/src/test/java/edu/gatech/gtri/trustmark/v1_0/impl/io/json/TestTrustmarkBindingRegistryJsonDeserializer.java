package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistry;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkBindingRegistrySystem;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by brad on 12/9/15.
 */
public class TestTrustmarkBindingRegistryJsonDeserializer extends AbstractTest {

    @Test
    public void testParseFullTipFile() throws Exception {
        File file = new File("./src/test/resources/edu/gatech/gtri/trustmark/v1_0/impl/io/json/TrustmarkBindingRegistry.json");
        String json = FileUtils.readFileToString(file);
        TrustmarkBindingRegistry trustmarkBindingRegistry = new TrustmarkBindingRegistryJsonDeserializer().deserialize(json);
    }
}
