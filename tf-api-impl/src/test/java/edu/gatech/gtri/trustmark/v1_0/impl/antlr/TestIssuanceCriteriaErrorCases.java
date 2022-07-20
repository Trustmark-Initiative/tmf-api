package edu.gatech.gtri.trustmark.v1_0.impl.antlr;

import edu.gatech.gtri.trustmark.v1_0.impl.AbstractTest;
import edu.gatech.gtri.trustmark.v1_0.model.AssessmentStepResult;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by brad on 7/27/16.
 */
public class TestIssuanceCriteriaErrorCases extends AbstractTest {

    @Test
    public void testMissingRightParen() throws Exception {
        logger.info("Testing error case with missing right paren...");

        List<AssessmentStepResult> results = new ArrayList<>();
        String expression = "yes(ALL";
        IssuanceCriteriaErrorListener errorListener = new IssuanceCriteriaErrorListener();

        IssuanceCriteriaExpressionEvaluator evaluator = execute(expression, results, errorListener);

        logger.debug("Asserting syntax error was detected properly...");
        assertThat(errorListener.hasSyntaxErrors(), equalTo(Boolean.TRUE));
        assertThat(errorListener.getSyntaxErrors(), contains("missing ')' at '<EOF>'"));

        logger.info("Missing right paren tested successfully");
    }//end testMissingRightParen()

    @Test
    public void testBadIdSyntax() throws Exception {
        logger.info("Testing error case with bad id syntax...");

        List<AssessmentStepResult> results = new ArrayList<>();
        String expression = "yes(test result 1)"; // bad because spaces are not allowed.
        IssuanceCriteriaErrorListener errorListener = new IssuanceCriteriaErrorListener();
        IssuanceCriteriaExpressionEvaluator evaluator = execute(expression, results, errorListener);

        logger.debug("Asserting syntax error was detected properly...");
        assertThat(errorListener.hasSyntaxErrors(), equalTo(Boolean.TRUE));
        assertThat(errorListener.getSyntaxErrors(), contains("mismatched input 'result' expecting ')'"));

        logger.info("Bad ID Syntax tested successfully");
    }//end testBadIdSyntax()

    private IssuanceCriteriaExpressionEvaluator execute(String expression, List<AssessmentStepResult> results, IssuanceCriteriaErrorListener errorListener)
            throws Exception {
        logger.debug("Testing expression: " + expression);
        IssuanceCriteriaExpressionEvaluator evaluator = new IssuanceCriteriaExpressionEvaluator(expression, results);
        ANTLRInputStream input = new ANTLRInputStream(expression);
        IssuanceCriteriaLexer lexer = new IssuanceCriteriaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        IssuanceCriteriaParser parser = new IssuanceCriteriaParser(tokens);
        parser.addErrorListener(errorListener);
        parser.addParseListener(evaluator);
        parser.issuanceCriteriaExpression();
        return evaluator;
    }
}
