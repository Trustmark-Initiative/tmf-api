package edu.gatech.gtri.trustmark.v1_0.impl.antlr;

import edu.gatech.gtri.trustmark.v1_0.util.TrustExpressionSyntaxException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.*;

/**
 * A {@link Parameterized} test which will run all test cases for the Issuance Criteria grammar found in the JSON files defined
 * by the private static testFiles member.
 * <br/><br/>
 * Created by brad on 4/28/16.
 */
@RunWith(Parameterized.class)
public class TestTrustExpressionAntlrGrammar extends AbstractAntlrGrammarTest {

    private String name;
    private String simpleTestString;
    private Boolean expectFailure = false;

    public TestTrustExpressionAntlrGrammar(String testName, String testString, Boolean expectFailure) {
        this.name = testName;
        this.simpleTestString = testString;
        this.expectFailure = expectFailure;
    }

    public static final String base = buildPath(".", "src", "test", "resources", "antlr", "trustExpression");
    public static final String[] testFiles = new String[]{
        base+File.separator+"simple-trust-expressions.json"
    };

    @Parameterized.Parameters(name = "{index} {1}")
    public static Collection<Object[]> data() throws Exception {
        List<Object[]> data = new ArrayList<>();

        for( String path : testFiles ){
            File f = new File(path);
            if( !f.exists() )
                throw new RuntimeException("Cannot find required file: "+f.getPath());

            JSONObject json = readJsonFile(f);
            String name = json.optString("Name");
            if( name == null )
                name = "<Unknown Name>";


            JSONArray array = json.optJSONArray("simpleTests");
            for( int i = 0; i < array.length(); i++ ){
                JSONObject testData = array.optJSONObject(i);
                String desc = testData.optString("Description");
                if( desc == null )
                    desc = "<Unknown Description>";
                boolean expectFailures = testData.optBoolean("Invalid");
                if( testData.optJSONArray("TrustExpression") != null ){
                    JSONArray trustExpressions = testData.optJSONArray("TrustExpression");
                    for( int j = 0; j < trustExpressions.length(); j++ ){
                        String trustExpression = trustExpressions.optString(j);
                        data.add(new Object[]{name + ": "+desc + " <"+j+">", trustExpression, expectFailures});
                    }
                }else if( testData.optString("TrustExpression") != null ){
                    data.add(new Object[]{name + ": "+desc, testData.optString("TrustExpression") , expectFailures});

                }
            }
        }

        return data;
    }

    @Test
    public void testTrustExpression() throws Exception{
        logger.info("Testing TrustExpression "+this.name+"...");

        if( this.simpleTestString == null || this.simpleTestString.trim().length() == 0 ){
            logger.error("Invalid test configuration("+this.name+") - missing required simple test string.");
            Assert.fail("Missing required simple test string!");
        }
        try {
            TrustExpressionUtils.validate(this.simpleTestString);
            if( this.expectFailure ){
                logger.error("Expected errors, but expression["+this.simpleTestString+"] had none.");
                Assert.fail("Missing Failure in trust expression["+this.simpleTestString+"]: Expected errors but there were none.");
            }
        }catch(TrustExpressionSyntaxException tese){
            if( !this.expectFailure ) {
                logger.error("Unexpected Failure in trust expression[" + this.simpleTestString + "]: " + tese);
                Assert.fail("Unexpected Failure in trust expression[" + this.simpleTestString + "]: " + tese);
            }
        }

        logger.info("SUCCESSFULLY Tested TrustExpression "+this.name+"!" + (this.expectFailure ? "  An error occurred just like expected." : ""));
    }//end testTrustExpression()



}
