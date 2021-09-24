package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionStringParser;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionStringParserFactory;
import org.junit.Assert;
import org.junit.Test;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataNonTerminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataReferenceTrustmarkDefinitionRequirement;

public class TestTrustExpressionStringParserImpl {

    @Test
    public void test() {
        TrustExpressionStringParserFactory trustExpressionStringParserFactory = FactoryLoader.getInstance(TrustExpressionStringParserFactory.class);
        Assert.assertNotNull(trustExpressionStringParserFactory);

        TrustExpressionStringParser trustExpressionStringParser = trustExpressionStringParserFactory.createDefaultParser();
        Assert.assertNotNull(trustExpressionStringParser);

        Assert.assertThrows(NullPointerException.class, () -> new TrustExpressionStringParserImpl(null));
        Assert.assertThrows(NullPointerException.class, () -> trustExpressionStringParser.parse(null));

        Assert.assertEquals(terminal(dataReferenceTrustmarkDefinitionRequirement("A")),
                trustExpressionStringParser.parse("A"));

        Assert.assertEquals(terminal(dataReferenceTrustmarkDefinitionRequirement("A")),
                trustExpressionStringParser.parse("(A)"));

        Assert.assertEquals(
                not(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), dataNonTerminal()),
                trustExpressionStringParser.parse("not A"));

        Assert.assertEquals(
                and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                trustExpressionStringParser.parse("A and B"));

        Assert.assertEquals(
                or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                trustExpressionStringParser.parse("A or B"));

        Assert.assertEquals(
                and(not(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), dataNonTerminal()), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                trustExpressionStringParser.parse("not A and B"));

        Assert.assertEquals(
                and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), not(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                trustExpressionStringParser.parse("A and not B"));

        Assert.assertEquals(
                not(and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                trustExpressionStringParser.parse("not (A and B)"));

        Assert.assertEquals(
                or(not(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), dataNonTerminal()), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                trustExpressionStringParser.parse("not A or B"));

        Assert.assertEquals(
                or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), not(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                trustExpressionStringParser.parse("A or not B"));

        Assert.assertEquals(
                not(or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                trustExpressionStringParser.parse("not (A or B)"));

        Assert.assertEquals(
                or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), and(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), terminal(dataReferenceTrustmarkDefinitionRequirement("C")), dataNonTerminal()), dataNonTerminal()),
                trustExpressionStringParser.parse("A or B and C"));

        Assert.assertEquals(
                or(and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), terminal(dataReferenceTrustmarkDefinitionRequirement("C")), dataNonTerminal()),
                trustExpressionStringParser.parse("A and B or C"));

        Assert.assertEquals(
                and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), or(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), terminal(dataReferenceTrustmarkDefinitionRequirement("C")), dataNonTerminal()), dataNonTerminal()),
                trustExpressionStringParser.parse("A and (B or C)"));
    }

    @Test
    public void testInvalid() {
        TrustExpressionStringParserFactory trustExpressionStringParserFactory = FactoryLoader.getInstance(TrustExpressionStringParserFactory.class);
        Assert.assertNotNull(trustExpressionStringParserFactory);

        TrustExpressionStringParser trustExpressionStringParser = trustExpressionStringParserFactory.createDefaultParser();
        Assert.assertNotNull(trustExpressionStringParser);

        Assert.assertThrows(org.jparsec.error.ParserException.class, () -> trustExpressionStringParser.parse("A and B)"));

    }

}
