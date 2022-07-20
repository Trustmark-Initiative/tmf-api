package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData;
import org.jparsec.Parser;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.contains;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.equal;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.exists;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.greaterThan;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.greaterThanOrEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.lessThan;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.lessThanOrEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.notEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.dataLiteralBoolean;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.dataLiteralDateTimeStamp;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.dataLiteralDecimal;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.dataLiteralString;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.dataNonTerminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.dataReferenceTrustmarkDefinitionParameter;
import static edu.gatech.gtri.trustmark.v1_0.tip.TrustExpressionData.dataReferenceTrustmarkDefinitionRequirement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestTrustExpressionParserFactoryJParsec {

    @Test
    public void testDataLiteralBoolean() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        assertNotNull(parser);

        assertEquals(
                terminal(dataLiteralBoolean(true)),
                parser.parse("true"));

        assertEquals(
                terminal(dataLiteralBoolean(false)),
                parser.parse("false"));
    }

    @Test
    public void testDataLiteralDateTimeStamp() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        assertNotNull(parser);

        final Instant now = Instant.now();
        final String nowString = now.toString();

        assertEquals(
                terminal(dataLiteralDateTimeStamp(now)),
                parser.parse(nowString));
    }

    @Test
    public void testDataLiteralDecimal() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        assertNotNull(parser);

        assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("0"))),
                parser.parse("0"));

        assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal(".0"))),
                parser.parse(".0"));

        assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("1."))),
                parser.parse("1."));

        assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("+0"))),
                parser.parse("+0"));

        assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("+.0"))),
                parser.parse("+.0"));

        assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("+1."))),
                parser.parse("+1."));

        assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("-0"))),
                parser.parse("-0"));

        assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("-.0"))),
                parser.parse("-.0"));

        assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("-1."))),
                parser.parse("-1."));
    }

    @Test
    public void testDataLiteralString() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        assertNotNull(parser);

        assertEquals(
                terminal(dataLiteralString("string")),
                parser.parse("\"string\""));

    }

    @Test
    public void testDataReferenceTrustmarkDefinitionRequirement() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        assertNotNull(parser);

        assertEquals(
                terminal(dataReferenceTrustmarkDefinitionRequirement("A")),
                parser.parse("A"));
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionParameter() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        assertNotNull(parser);

        assertEquals(
                terminal(dataReferenceTrustmarkDefinitionParameter("A", "A")),
                parser.parse("A.A"));
    }

    @Test
    public void testNonTerminal() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        assertNotNull(parser);

        assertEquals(
                contains(terminal(dataReferenceTrustmarkDefinitionParameter("A", "A")), terminal(dataLiteralString("string")), dataNonTerminal()),
                parser.parse("contains(A.A, \"string\")"));

        assertEquals(
                exists(terminal(dataReferenceTrustmarkDefinitionParameter("A", "A")), dataNonTerminal()),
                parser.parse("exists(A.A)"));

        assertEquals(
                lessThan(terminal(dataLiteralString("string")), terminal(dataLiteralDecimal(new BigDecimal("1.0"))), dataNonTerminal()),
                parser.parse("\"string\" < 1.0"));

        assertEquals(
                lessThanOrEqual(terminal(dataLiteralString("string")), terminal(dataLiteralDecimal(new BigDecimal("1.0"))), dataNonTerminal()),
                parser.parse("\"string\" <= 1.0"));

        assertEquals(
                greaterThanOrEqual(terminal(dataLiteralString("string")), terminal(dataLiteralDecimal(new BigDecimal("1.0"))), dataNonTerminal()),
                parser.parse("\"string\" >= 1.0"));

        assertEquals(
                greaterThan(terminal(dataLiteralString("string")), terminal(dataLiteralDecimal(new BigDecimal("1.0"))), dataNonTerminal()),
                parser.parse("\"string\" > 1.0"));

        assertEquals(
                equal(terminal(dataLiteralString("string")), terminal(dataLiteralDecimal(new BigDecimal("1.0"))), dataNonTerminal()),
                parser.parse("\"string\" == 1.0"));

        assertEquals(
                notEqual(terminal(dataLiteralString("string")), terminal(dataLiteralDecimal(new BigDecimal("1.0"))), dataNonTerminal()),
                parser.parse("\"string\" != 1.0"));

        assertEquals(
                not(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), dataNonTerminal()),
                parser.parse("not A"));

        assertEquals(
                and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                parser.parse("A and B"));

        assertEquals(
                or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                parser.parse("A or B"));

        assertEquals(
                and(not(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), dataNonTerminal()), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                parser.parse("not A and B"));

        assertEquals(
                and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), not(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                parser.parse("A and not B"));

        assertEquals(
                not(and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                parser.parse("not (A and B)"));

        assertEquals(
                or(not(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), dataNonTerminal()), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                parser.parse("not A or B"));

        assertEquals(
                or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), not(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                parser.parse("A or not B"));

        assertEquals(
                not(or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                parser.parse("not (A or B)"));

        assertEquals(
                or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), and(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), terminal(dataReferenceTrustmarkDefinitionRequirement("C")), dataNonTerminal()), dataNonTerminal()),
                parser.parse("A or B and C"));

        assertEquals(
                or(and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), terminal(dataReferenceTrustmarkDefinitionRequirement("C")), dataNonTerminal()),
                parser.parse("A and B or C"));

        assertEquals(
                and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), or(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), terminal(dataReferenceTrustmarkDefinitionRequirement("C")), dataNonTerminal()), dataNonTerminal()),
                parser.parse("A and (B or C)"));
    }
}
