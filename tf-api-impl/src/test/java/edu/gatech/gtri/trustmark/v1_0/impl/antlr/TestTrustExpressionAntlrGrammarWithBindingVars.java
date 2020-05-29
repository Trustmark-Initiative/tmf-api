package edu.gatech.gtri.trustmark.v1_0.impl.antlr;

import edu.gatech.gtri.trustmark.v1_0.util.TrustExpressionHasUndeclaredIdException;
import edu.gatech.gtri.trustmark.v1_0.util.TrustExpressionSyntaxException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A {@link Parameterized} test which will run all test cases for the Issuance Criteria grammar found in the JSON files defined
 * by the private static testFiles member.
 * <br/><br/>
 * Created by brad on 4/28/16.
 */
@RunWith(Parameterized.class)
public class TestTrustExpressionAntlrGrammarWithBindingVars extends AbstractAntlrGrammarTest {

    private String name;
    private String simpleTestString;
    private Boolean expectFailure = false;
    private List<String> bindingVars;

    public TestTrustExpressionAntlrGrammarWithBindingVars(String testName, String testString, Boolean expectFailure, List<String> bindingVars) {
        this.name = testName;
        this.simpleTestString = testString;
        this.expectFailure = expectFailure;
        this.bindingVars = bindingVars;
    }

    public static final String base = buildPath(".", "src", "test", "resources", "antlr", "trustExpression");
    public static final String[] testFiles = new String[]{
        base+File.separator+"trust-expression-id-binding-tests.json"
    };

    @Parameterized.Parameters(name = "{index}_{1}")
    public static Collection<Object[]> data() throws Exception {
        logger.debug("Building test data...");
        List<Object[]> data = new ArrayList<>();

        try {
            for (String path : testFiles) {
                File f = new File(path);
                if (!f.exists())
                    throw new RuntimeException("Cannot find required file: " + f.getPath());

                JSONObject json = readJsonFile(f);
                String name = json.optString("Name");
                if (name == null)
                    name = "<Unknown Name>";

                JSONArray array = json.optJSONArray("IdentifierTests");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject testData = array.optJSONObject(i);
                    String desc = testData.optString("Description");
                    if (desc == null)
                        desc = "<Unknown Description>";
                    boolean expectFailures = testData.optBoolean("Invalid");
                    List<String> bindingVars = new ArrayList<>();
                    JSONArray ids = testData.getJSONArray("Identifiers");
                    for (int k = 0; k < ids.length(); k++) {
                        bindingVars.add(ids.getString(k));
                    }

                    if (testData.optJSONArray("TrustExpression") != null) {
                        JSONArray trustExpressions = testData.optJSONArray("TrustExpression");
                        for (int j = 0; j < trustExpressions.length(); j++) {
                            String trustExpression = trustExpressions.optString(j);
                            data.add(new Object[]{name + ": " + desc + " <" + j + ">", trustExpression, expectFailures, bindingVars});
                        }
                    } else if (testData.optString("TrustExpression") != null) {
                        data.add(new Object[]{name + ": " + desc, testData.optString("TrustExpression"), expectFailures, bindingVars});

                    }
                }
            }
        }catch(Throwable t){
            logger.error("Error building test data!", t);
            throw t;
        }

        logger.debug("Successfully built test data!");
        return data;
    }



    @Test
    public void testTrustExpressionWithBindingVars() throws Exception{
        logger.info("Testing TrustExpression "+this.name+"...");

        if( this.simpleTestString == null || this.simpleTestString.trim().length() == 0 ){
            logger.error("Invalid test configuration("+this.name+") - missing required simple test string.");
            Assert.fail("Missing required simple test string!");
        }
        try {
            TrustExpressionUtils.validateWithBindings(this.simpleTestString, this.bindingVars);
            if( this.expectFailure ){
                logger.error("Expected errors, but expression["+this.simpleTestString+"] had none.");
                Assert.fail("Missing Failure in trust expression["+this.simpleTestString+"]: Expected errors but there were none.");
            }
        }catch(TrustExpressionSyntaxException tese){
            if (!this.expectFailure) {
                logger.error("Unexpected Failure in trust expression[" + this.simpleTestString + "]: " + tese);
                Assert.fail("Unexpected Failure in trust expression[" + this.simpleTestString + "]: " + tese);
            }
        }catch( TrustExpressionHasUndeclaredIdException tehuie ){
            if (!this.expectFailure) {
                logger.error("Unexpected Failure in trust expression[" + this.simpleTestString + "]: " + tehuie);
                Assert.fail("Unexpected Failure in trust expression[" + this.simpleTestString + "]: " + tehuie);
            }
        }

        logger.info("SUCCESSFULLY Tested TrustExpression "+this.name+"!" + (this.expectFailure ? "  An error occurred just like expected." : ""));
    }//end testTrustExpression()



}
