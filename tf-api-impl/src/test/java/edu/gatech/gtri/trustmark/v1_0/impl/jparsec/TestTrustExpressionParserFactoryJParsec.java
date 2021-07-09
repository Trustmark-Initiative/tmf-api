package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import org.gtri.fj.product.Unit;
import org.jparsec.Parser;
import org.junit.Assert;
import org.junit.Test;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.terminal;
import static org.gtri.fj.product.Unit.unit;

public class TestTrustExpressionParserFactoryJParsec {

    @Test
    public void test() {

        Parser<TrustExpression<Unit, String>> parser = TrustExpressionParserFactoryJParsec.parser();

        Assert.assertNotNull(parser);

        Assert.assertEquals(terminal("A"),
                parser.parse("A"));

        Assert.assertEquals(terminal("A"),
                parser.parse("(A)"));

        Assert.assertEquals(
                not(terminal("A"), unit()),
                parser.parse("not A"));

        Assert.assertEquals(
                and(terminal("A"), terminal("B"), unit()),
                parser.parse("A and B"));

        Assert.assertEquals(
                or(terminal("A"), terminal("B"), unit()),
                parser.parse("A or B"));

        Assert.assertEquals(
                and(not(terminal("A"), unit()), terminal("B"), unit()),
                parser.parse("not A and B"));

        Assert.assertEquals(
                and(terminal("A"), not(terminal("B"), unit()), unit()),
                parser.parse("A and not B"));

        Assert.assertEquals(
                not(and(terminal("A"), terminal("B"), unit()), unit()),
                parser.parse("not (A and B)"));

        Assert.assertEquals(
                or(not(terminal("A"), unit()), terminal("B"), unit()),
                parser.parse("not A or B"));

        Assert.assertEquals(
                or(terminal("A"), not(terminal("B"), unit()), unit()),
                parser.parse("A or not B"));

        Assert.assertEquals(
                not(or(terminal("A"), terminal("B"), unit()), unit()),
                parser.parse("not (A or B)"));

        Assert.assertEquals(
                or(terminal("A"), and(terminal("B"), terminal("C"), unit()), unit()),
                parser.parse("A or B and C"));

        Assert.assertEquals(
                or(and(terminal("A"), terminal("B"), unit()), terminal("C"), unit()),
                parser.parse("A and B or C"));

        Assert.assertEquals(
                and(terminal("A"), or(terminal("B"), terminal("C"), unit()), unit()),
                parser.parse("A and (B or C)"));
    }
}
