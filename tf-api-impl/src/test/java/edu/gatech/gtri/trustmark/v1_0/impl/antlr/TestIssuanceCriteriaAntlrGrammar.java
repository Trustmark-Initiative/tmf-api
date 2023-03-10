package edu.gatech.gtri.trustmark.v1_0.impl.antlr;

import edu.gatech.gtri.trustmark.v1_0.impl.model.AssessmentStepResultImpl;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStepResult;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStepResultType;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentJsonSerializer.*;

/**
 * A {@link Parameterized} test which will run all test cases for the Issuance
 * Criteria grammar found in the JSON files defined by the private static
 * testFiles member. <br/><br/> Created by brad on 4/28/16.
 */
public class TestIssuanceCriteriaAntlrGrammar extends AbstractAntlrGrammarTest {

    public static final String base = buildPath(".", "src", "test", "resources", "TDs", "issuanceCriteria");
    public static final String[] testFiles = new String[]{
            base + File.separator + "simple_predicate_only_tests.json",
            base + File.separator + "andOr_tests.json",
            base + File.separator + "not_tests.json",
            base + File.separator + "real_world.json"
    };

    public static Stream<Arguments> data() throws Exception {
        List<Object[]> data = new ArrayList<>();
        for (String testFilePath : testFiles) {
            File testFile = new File(testFilePath);
            JSONObject jsonObject = readJsonFile(testFile);
            JSONArray resultSet = jsonObject.optJSONArray("resultSet");
            if (resultSet == null)
                throw new UnsupportedOperationException("Invalid test set!  No JSONArray named 'resultSet' in file.");
            List<AssessmentStepResult> results = new ArrayList<>();
            for (int i = 0; i < resultSet.length(); i++) {
                JSONObject result = resultSet.optJSONObject(i);
                results.add(toResultImpl(result, i));
            }


            JSONArray testSet = jsonObject.optJSONArray("testSet");
            if (testSet == null)
                throw new UnsupportedOperationException("Expecting testSet, but no JSONArray named 'testSet' found in file");

            for (int i = 0; i < testSet.length(); i++) {
                JSONObject testObj = testSet.getJSONObject(i);
                String expression = testObj.getString("expression");
                Boolean hasError = testObj.optBoolean("hasError", false);
                Boolean satisfies = testObj.optBoolean("satisfies", false);
                Map<Object, Object> curData = new HashMap<>();
                curData.put("results", results);
                curData.put("expression", expression);
                curData.put("hasError", hasError);
                curData.put("satisfies", satisfies);
                data.add(new Object[]{curData, testFile.getName() + " #" + (i + 1)});
            }
        }

        return data.stream().map(objectArray -> () -> objectArray);
    }

    private static AssessmentStepResultImpl toResultImpl(JSONObject json, Integer index) {
        AssessmentStepResultImpl resultImpl = new AssessmentStepResultImpl();
        if (json.optString(ATTRIBUTE_KEY_JSON_ID, null) != null)
            resultImpl.setAssessmentStepId(json.optString(ATTRIBUTE_KEY_JSON_ID));
        else
            resultImpl.setAssessmentStepId("step" + (index + 1));

        if (json.optInt("number", -1) != -1)
            resultImpl.setAssessmentStepNumber(json.optInt("number"));
        else
            resultImpl.setAssessmentStepNumber(index + 1);

        if (json.optString("result", null) != null)
            resultImpl.setResult(AssessmentStepResultType.fromString(json.optString("result")));
        else
            resultImpl.setResult(AssessmentStepResultType.UNKNOWN);

        return resultImpl;
    }

    @ParameterizedTest(name = "{index} {1}")
    @MethodSource("data")
    public void testIssuanceCriteria(Map<Object, Object> data) throws Exception {
        String expression = (String) data.get("expression");
        Boolean hasError = (Boolean) data.get("hasError");
        Boolean satisfies = (Boolean) data.get("satisfies");
        List<AssessmentStepResult> results = (List) data.get("results");

        logger.debug("Testing expression: " + expression);
        IssuanceCriteriaExpressionEvaluator evaluator = new IssuanceCriteriaExpressionEvaluator(expression, results);
        ANTLRInputStream input = new ANTLRInputStream(expression);
        IssuanceCriteriaLexer lexer = new IssuanceCriteriaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        IssuanceCriteriaParser parser = new IssuanceCriteriaParser(tokens);
        parser.addErrorListener(new IssuanceCriteriaErrorListener());
        parser.addParseListener(evaluator);
        parser.issuanceCriteriaExpression();

        if (hasError) {
            assertThat("Asserting error state", evaluator.isHasError(), equalTo(true));
        } else {
            assertThat("Asserting that satisfies matches", evaluator.getSatisfied(), equalTo(satisfies));

        }

        logger.info("Evaluator returned: " + evaluator.getSatisfied());
    }//end testIssuanceCriteriaAntlrGrammar()
}
