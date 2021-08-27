package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData;
import org.jparsec.Parser;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.contains;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.equal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.exists;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.greaterThan;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.greaterThanOrEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.lessThan;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.lessThanOrEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.notEqual;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.terminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataLiteralBoolean;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataLiteralDateTimeStamp;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataLiteralDecimal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataLiteralString;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataNonTerminal;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataReferenceTrustmarkDefinitionParameter;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData.dataReferenceTrustmarkDefinitionRequirement;

public class TestTrustExpressionParserFactoryJParsec {

    @Test
    public void testDataLiteralBoolean() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        Assert.assertNotNull(parser);

        Assert.assertEquals(
                terminal(dataLiteralBoolean(true)),
                parser.parse("true"));

        Assert.assertEquals(
                terminal(dataLiteralBoolean(false)),
                parser.parse("false"));
    }

    @Test
    public void testDataLiteralDateTimeStamp() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        Assert.assertNotNull(parser);

        final Instant now = Instant.now();
        final String nowString = now.toString();

        Assert.assertEquals(
                terminal(dataLiteralDateTimeStamp(now)),
                parser.parse(nowString));
    }

    @Test
    public void testDataLiteralDecimal() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        Assert.assertNotNull(parser);

        Assert.assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("0"))),
                parser.parse("0"));

        Assert.assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal(".0"))),
                parser.parse(".0"));

        Assert.assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("1."))),
                parser.parse("1."));

        Assert.assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("+0"))),
                parser.parse("+0"));

        Assert.assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("+.0"))),
                parser.parse("+.0"));

        Assert.assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("+1."))),
                parser.parse("+1."));

        Assert.assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("-0"))),
                parser.parse("-0"));

        Assert.assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("-.0"))),
                parser.parse("-.0"));

        Assert.assertEquals(
                terminal(dataLiteralDecimal(new BigDecimal("-1."))),
                parser.parse("-1."));
    }

    @Test
    public void testDataLiteralString() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        Assert.assertNotNull(parser);

        Assert.assertEquals(
                terminal(dataLiteralString("string")),
                parser.parse("'string'"));

    }

    @Test
    public void testDataReferenceTrustmarkDefinitionRequirement() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        Assert.assertNotNull(parser);

        Assert.assertEquals(
                terminal(dataReferenceTrustmarkDefinitionRequirement("A")),
                parser.parse("A"));
    }

    @Test
    public void testDataReferenceTrustmarkDefinitionParameter() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        Assert.assertNotNull(parser);

        Assert.assertEquals(
                terminal(dataReferenceTrustmarkDefinitionParameter("A", "A")),
                parser.parse("A.A"));
    }

    @Test
    public void testNonTerminal() {

        final Parser<TrustExpression<TrustExpressionData>> parser = TrustExpressionParserFactoryJParsec.parser();

        Assert.assertNotNull(parser);

        Assert.assertEquals(
                contains(terminal(dataReferenceTrustmarkDefinitionParameter("A", "A")), terminal(dataLiteralString("string")), dataNonTerminal()),
                parser.parse("contains(A.A, 'string')"));

        Assert.assertEquals(
                exists(terminal(dataReferenceTrustmarkDefinitionParameter("A", "A")), dataNonTerminal()),
                parser.parse("exists(A.A)"));

        Assert.assertEquals(
                lessThan(terminal(dataLiteralString("string")), terminal(dataLiteralDecimal(new BigDecimal("1.0"))), dataNonTerminal()),
                parser.parse("'string' < 1.0"));

        Assert.assertEquals(
                lessThanOrEqual(terminal(dataLiteralString("string")), terminal(dataLiteralDecimal(new BigDecimal("1.0"))), dataNonTerminal()),
                parser.parse("'string' <= 1.0"));

        Assert.assertEquals(
                greaterThanOrEqual(terminal(dataLiteralString("string")), terminal(dataLiteralDecimal(new BigDecimal("1.0"))), dataNonTerminal()),
                parser.parse("'string' >= 1.0"));

        Assert.assertEquals(
                greaterThan(terminal(dataLiteralString("string")), terminal(dataLiteralDecimal(new BigDecimal("1.0"))), dataNonTerminal()),
                parser.parse("'string' > 1.0"));

        Assert.assertEquals(
                equal(terminal(dataLiteralString("string")), terminal(dataLiteralDecimal(new BigDecimal("1.0"))), dataNonTerminal()),
                parser.parse("'string' == 1.0"));

        Assert.assertEquals(
                notEqual(terminal(dataLiteralString("string")), terminal(dataLiteralDecimal(new BigDecimal("1.0"))), dataNonTerminal()),
                parser.parse("'string' != 1.0"));

        Assert.assertEquals(
                not(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), dataNonTerminal()),
                parser.parse("not A"));

        Assert.assertEquals(
                and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                parser.parse("A and B"));

        Assert.assertEquals(
                or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                parser.parse("A or B"));

        Assert.assertEquals(
                and(not(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), dataNonTerminal()), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                parser.parse("not A and B"));

        Assert.assertEquals(
                and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), not(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                parser.parse("A and not B"));

        Assert.assertEquals(
                not(and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                parser.parse("not (A and B)"));

        Assert.assertEquals(
                or(not(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), dataNonTerminal()), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()),
                parser.parse("not A or B"));

        Assert.assertEquals(
                or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), not(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                parser.parse("A or not B"));

        Assert.assertEquals(
                not(or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), dataNonTerminal()),
                parser.parse("not (A or B)"));

        Assert.assertEquals(
                or(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), and(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), terminal(dataReferenceTrustmarkDefinitionRequirement("C")), dataNonTerminal()), dataNonTerminal()),
                parser.parse("A or B and C"));

        Assert.assertEquals(
                or(and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), terminal(dataReferenceTrustmarkDefinitionRequirement("B")), dataNonTerminal()), terminal(dataReferenceTrustmarkDefinitionRequirement("C")), dataNonTerminal()),
                parser.parse("A and B or C"));

        Assert.assertEquals(
                and(terminal(dataReferenceTrustmarkDefinitionRequirement("A")), or(terminal(dataReferenceTrustmarkDefinitionRequirement("B")), terminal(dataReferenceTrustmarkDefinitionRequirement("C")), dataNonTerminal()), dataNonTerminal()),
                parser.parse("A and (B or C)"));
    }
}
