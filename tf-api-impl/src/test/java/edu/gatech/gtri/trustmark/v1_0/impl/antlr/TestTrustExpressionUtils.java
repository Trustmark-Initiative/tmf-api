package edu.gatech.gtri.trustmark.v1_0.impl.antlr;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 3/8/17
 */
public class TestTrustExpressionUtils extends AbstractTest {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = LogManager.getLogger(TestTrustExpressionUtils.class);
    //==================================================================================================================
    //  TEST METHODS
    //==================================================================================================================
    @Test
    public void testBindingVarsCheck() {
        log.info("Checking a valid case for binding vars...");
        String te = "td1 and td2";
        assertThat(te, notNullValue());
        List<String> bindingVars = new ArrayList<>();
        bindingVars.add("td1");
        bindingVars.add("td2");

        try {
            TrustExpressionUtils.validateWithBindings(te, bindingVars);
        }catch(Throwable t){
            log.error("Error - received unexpected exception!", t);
            Assert.fail("Failure validating expression: "+te+" with binding vars: "+bindingVars);
        }
    }

}/* end TestTrustExpressionUtils */