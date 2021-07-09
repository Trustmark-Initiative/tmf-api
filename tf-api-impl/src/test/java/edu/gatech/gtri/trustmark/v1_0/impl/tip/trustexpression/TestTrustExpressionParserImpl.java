package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParser;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionParserFactory;
import org.junit.Assert;
import org.junit.Test;

import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.terminal;
import static org.gtri.fj.product.Unit.unit;

public class TestTrustExpressionParserImpl {

    @Test
    public void test() {
        TrustExpressionParserFactory trustExpressionParserFactory = FactoryLoader.getInstance(TrustExpressionParserFactory.class);
        Assert.assertNotNull(trustExpressionParserFactory);

        TrustExpressionParser trustExpressionParser = trustExpressionParserFactory.createDefaultParser();
        Assert.assertNotNull(trustExpressionParser);

        Assert.assertThrows(NullPointerException.class, () -> new TrustExpressionParserImpl(null));
        Assert.assertThrows(NullPointerException.class, () -> trustExpressionParser.parse(null));

        Assert.assertEquals(terminal("A"),
                trustExpressionParser.parse("A"));

        Assert.assertEquals(terminal("A"),
                trustExpressionParser.parse("(A)"));

        Assert.assertEquals(
                not(terminal("A"), unit()),
                trustExpressionParser.parse("not A"));

        Assert.assertEquals(
                and(terminal("A"), terminal("B"), unit()),
                trustExpressionParser.parse("A and B"));

        Assert.assertEquals(
                or(terminal("A"), terminal("B"), unit()),
                trustExpressionParser.parse("A or B"));

        Assert.assertEquals(
                and(not(terminal("A"), unit()), terminal("B"), unit()),
                trustExpressionParser.parse("not A and B"));

        Assert.assertEquals(
                and(terminal("A"), not(terminal("B"), unit()), unit()),
                trustExpressionParser.parse("A and not B"));

        Assert.assertEquals(
                not(and(terminal("A"), terminal("B"), unit()), unit()),
                trustExpressionParser.parse("not (A and B)"));

        Assert.assertEquals(
                or(not(terminal("A"), unit()), terminal("B"), unit()),
                trustExpressionParser.parse("not A or B"));

        Assert.assertEquals(
                or(terminal("A"), not(terminal("B"), unit()), unit()),
                trustExpressionParser.parse("A or not B"));

        Assert.assertEquals(
                not(or(terminal("A"), terminal("B"), unit()), unit()),
                trustExpressionParser.parse("not (A or B)"));

        Assert.assertEquals(
                or(terminal("A"), and(terminal("B"), terminal("C"), unit()), unit()),
                trustExpressionParser.parse("A or B and C"));

        Assert.assertEquals(
                or(and(terminal("A"), terminal("B"), unit()), terminal("C"), unit()),
                trustExpressionParser.parse("A and B or C"));

        Assert.assertEquals(
                and(terminal("A"), or(terminal("B"), terminal("C"), unit()), unit()),
                trustExpressionParser.parse("A and (B or C)"));
    }

}
