package edu.gatech.gtri.trustmark.v1_0.impl.tip;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionStringParser;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionStringParserFactory;
import org.junit.jupiter.api.Test;

import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.dataNonTerminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.dataReferenceTrustmarkDefinitionRequirement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTrustExpressionStringParserImpl {

    @Test
    public void test() {
        TrustExpressionStringParserFactory trustExpressionStringParserFactory = FactoryLoader.getInstance(TrustExpressionStringParserFactory.class);
        assertNotNull(trustExpressionStringParserFactory);

        TrustExpressionStringParser trustExpressionStringParser = trustExpressionStringParserFactory.createDefaultParser();
        assertNotNull(trustExpressionStringParser);

        assertThrows(NullPointerException.class, () -> new TrustExpressionStringParserImpl(null));
        assertThrows(NullPointerException.class, () -> trustExpressionStringParser.parse(null));

        assertEquals(terminal(dataReferenceTrustmarkDefinitionRequirement("A")),
                trustExpressionStringParser.parse("A"));

        assertEquals(terminal(dataReferenceTrustmarkDefinitionRequirement("A")),
                trustExpressionStringParser.parse("(A)"));

        assertEquals(
                not(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), dataNonTerminal()),
                trustExpressionStringParser.parse("not A"));

        assertEquals(
                and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                trustExpressionStringParser.parse("A and B"));

        assertEquals(
                or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                trustExpressionStringParser.parse("A or B"));

        assertEquals(
                and(not(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), dataNonTerminal()), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                trustExpressionStringParser.parse("not A and B"));

        assertEquals(
                and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), not(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                trustExpressionStringParser.parse("A and not B"));

        assertEquals(
                not(and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                trustExpressionStringParser.parse("not (A and B)"));

        assertEquals(
                or(not(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), dataNonTerminal()), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                trustExpressionStringParser.parse("not A or B"));

        assertEquals(
                or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), not(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                trustExpressionStringParser.parse("A or not B"));

        assertEquals(
                not(or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                trustExpressionStringParser.parse("not (A or B)"));

        assertEquals(
                or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), and(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), terminal(dataReferenceTrustmarkDefinitionRequirement("C")), dataNonTerminal()), dataNonTerminal()),
                trustExpressionStringParser.parse("A or B and C"));

        assertEquals(
                or(and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), terminal(dataReferenceTrustmarkDefinitionRequirement("C")), dataNonTerminal()),
                trustExpressionStringParser.parse("A and B or C"));

        assertEquals(
                and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), or(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), terminal(dataReferenceTrustmarkDefinitionRequirement("C")), dataNonTerminal()), dataNonTerminal()),
                trustExpressionStringParser.parse("A and (B or C)"));
    }

    @Test
    public void testInvalid() {
        TrustExpressionStringParserFactory trustExpressionStringParserFactory = FactoryLoader.getInstance(TrustExpressionStringParserFactory.class);
        assertNotNull(trustExpressionStringParserFactory);

        TrustExpressionStringParser trustExpressionStringParser = trustExpressionStringParserFactory.createDefaultParser();
        assertNotNull(trustExpressionStringParser);

        assertThrows(org.jparsec.error.ParserException.class, () -> trustExpressionStringParser.parse("A and B)"));

    }

}
