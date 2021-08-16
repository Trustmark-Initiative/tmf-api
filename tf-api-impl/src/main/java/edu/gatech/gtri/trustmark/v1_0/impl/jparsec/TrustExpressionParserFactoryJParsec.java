package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenLiteralBooleanFalse;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenLiteralBooleanTrue;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenLiteralDateTimeStamp;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenLiteralDecimal;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenLiteralString;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorContains;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorEqual;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorExists;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorGreaterThan;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorGreaterThanOrEqual;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorLessThan;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorLessThanOrEqual;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorNotEqual;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenSeparatorComma;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenSeparatorDot;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionData;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorAnd;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorBinary;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorContains;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorEqual;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorGreaterThan;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorGreaterThanOrEqual;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorLessThan;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorLessThanOrEqual;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorNotEqual;
import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionOperator.TrustExpressionOperatorOr;
import org.gtri.fj.function.F1;
import org.gtri.fj.product.P;
import org.jparsec.Parser;
import org.jparsec.Parsers;

import java.math.BigDecimal;
import java.time.Instant;

import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenIdentifier;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorAnd;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorNot;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorOr;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenSeparatorParenthesisLeft;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenSeparatorParenthesisRight;
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
import static java.lang.String.format;
import static org.gtri.fj.data.List.iterableList;

public final class TrustExpressionParserFactoryJParsec {
    private TrustExpressionParserFactoryJParsec() {
    }

    // @formatter:off
    private static final Parser<LexerTokenOperatorOr>                 parserTokenOperatorOr                  = Parsers.tokenType(LexerTokenOperatorOr.class,                  format("'%s'", LexerTokenOperatorOr.OPERATOR_OR.getValue()));
    private static final Parser<LexerTokenOperatorAnd>                parserTokenOperatorAnd                 = Parsers.tokenType(LexerTokenOperatorAnd.class,                 format("'%s'", LexerTokenOperatorAnd.OPERATOR_AND.getValue()));
    private static final Parser<LexerTokenOperatorNot>                parserTokenOperatorNot                 = Parsers.tokenType(LexerTokenOperatorNot.class,                 format("'%s'", LexerTokenOperatorNot.OPERATOR_NOT.getValue()));
    private static final Parser<LexerTokenOperatorEqual>              parserTokenOperatorEqual               = Parsers.tokenType(LexerTokenOperatorEqual.class,               format("'%s'", LexerTokenOperatorEqual.OPERATOR_EQUAL.getValue()));
    private static final Parser<LexerTokenOperatorNotEqual>           parserTokenOperatorNotEqual            = Parsers.tokenType(LexerTokenOperatorNotEqual.class,            format("'%s'", LexerTokenOperatorNotEqual.OPERATOR_NOT_EQUAL.getValue()));
    private static final Parser<LexerTokenOperatorGreaterThan>        parserTokenOperatorGreaterThan         = Parsers.tokenType(LexerTokenOperatorGreaterThan.class,         format("'%s'", LexerTokenOperatorGreaterThan.OPERATOR_GREATER_THAN.getValue()));
    private static final Parser<LexerTokenOperatorLessThan>           parserTokenOperatorLessThan            = Parsers.tokenType(LexerTokenOperatorLessThan.class,            format("'%s'", LexerTokenOperatorLessThan.OPERATOR_LESS_THAN.getValue()));
    private static final Parser<LexerTokenOperatorGreaterThanOrEqual> parserTokenOperatorGreaterThanOrEqual  = Parsers.tokenType(LexerTokenOperatorGreaterThanOrEqual.class,  format("'%s'", LexerTokenOperatorGreaterThanOrEqual.OPERATOR_GREATER_THAN_OR_EQUAL.getValue()));
    private static final Parser<LexerTokenOperatorLessThanOrEqual>    parserTokenOperatorLessThanOrEqual     = Parsers.tokenType(LexerTokenOperatorLessThanOrEqual.class,     format("'%s'", LexerTokenOperatorLessThanOrEqual.OPERATOR_LESS_THAN_OR_EQUAL.getValue()));
    private static final Parser<LexerTokenOperatorContains>           parserTokenOperatorContains            = Parsers.tokenType(LexerTokenOperatorContains.class,            format("'%s'", LexerTokenOperatorContains.OPERATOR_CONTAINS.getValue()));
    private static final Parser<LexerTokenOperatorExists>             parserTokenOperatorExists              = Parsers.tokenType(LexerTokenOperatorExists.class,              format("'%s'", LexerTokenOperatorExists.OPERATOR_EXISTS.getValue()));
    private static final Parser<LexerTokenSeparatorParenthesisLeft>   parserTokenSeparatorParenthesisLeft    = Parsers.tokenType(LexerTokenSeparatorParenthesisLeft.class,    format("'%s'", LexerTokenSeparatorParenthesisLeft.SEPARATOR_PARENTHESIS_LEFT.getValue()));
    private static final Parser<LexerTokenSeparatorParenthesisRight>  parserTokenSeparatorParenthesisRight   = Parsers.tokenType(LexerTokenSeparatorParenthesisRight.class,   format("'%s'", LexerTokenSeparatorParenthesisRight.SEPARATOR_PARENTHESIS_RIGHT.getValue()));
    private static final Parser<LexerTokenSeparatorDot>               parserTokenSeparatorDot                = Parsers.tokenType(LexerTokenSeparatorDot.class,                format("'%s'", LexerTokenSeparatorDot.SEPARATOR_DOT.getValue()));
    private static final Parser<LexerTokenSeparatorComma>             parserTokenSeparatorComma              = Parsers.tokenType(LexerTokenSeparatorComma.class,              format("'%s'", LexerTokenSeparatorComma.SEPARATOR_COMMA.getValue()));
    private static final Parser<LexerTokenLiteralBooleanTrue>         parserTokenLiteralBooleanTrue          = Parsers.tokenType(LexerTokenLiteralBooleanTrue.class,          format("'%s'", LexerTokenLiteralBooleanTrue.LITERAL_BOOLEAN_TRUE.getValue()));
    private static final Parser<LexerTokenLiteralBooleanFalse>        parserTokenLiteralBooleanFalse         = Parsers.tokenType(LexerTokenLiteralBooleanFalse.class,         format("'%s'", LexerTokenLiteralBooleanFalse.LITERAL_BOOLEAN_FALSE.getValue()));
    private static final Parser<LexerTokenIdentifier>                 parserTokenIdentifier                  = Parsers.tokenType(LexerTokenIdentifier.class,                  "identifier");
    private static final Parser<LexerTokenLiteralDateTimeStamp>       parserTokenLiteralDateTimeStamp        = Parsers.tokenType(LexerTokenLiteralDateTimeStamp.class,        "literal datetimestamp");
    private static final Parser<LexerTokenLiteralDecimal>             parserTokenLiteralDecimal              = Parsers.tokenType(LexerTokenLiteralDecimal.class,              "literal decimal");
    private static final Parser<LexerTokenLiteralString>              parserTokenLiteralString               = Parsers.tokenType(LexerTokenLiteralString.class,               "literal string");
    // @formatter:on

    private static final Parser.Reference<TrustExpression<TrustExpressionData>> parserExpressionOrReference = Parser.newReference();
    private static final Parser.Reference<TrustExpression<TrustExpressionData>> parserExpressionAndReference = Parser.newReference();
    private static final Parser.Reference<TrustExpression<TrustExpressionData>> parserExpressionNotReference = Parser.newReference();
    private static final Parser.Reference<TrustExpression<TrustExpressionData>> parserExpressionRelationReference = Parser.newReference();
    private static final Parser.Reference<TrustExpression<TrustExpressionData>> parserExpressionEqualityReference = Parser.newReference();
    private static final Parser.Reference<TrustExpression<TrustExpressionData>> parserExpressionAtomReference = Parser.newReference();

    private static final F1<TrustExpressionOperatorAnd, TrustExpression<TrustExpressionData>> fTrustExpressionOperatorAndThrowsRuntimeException = operator -> {
        throw new RuntimeException();
    };

    private static final F1<TrustExpressionOperatorOr, TrustExpression<TrustExpressionData>> fTrustExpressionOperatorOrThrowsRuntimeException = operator -> {
        throw new RuntimeException();
    };

    private static final F1<TrustExpressionOperatorLessThan, TrustExpression<TrustExpressionData>> fTrustExpressionOperatorLessThanThrowsRuntimeException = operator -> {
        throw new RuntimeException();
    };

    private static final F1<TrustExpressionOperatorLessThanOrEqual, TrustExpression<TrustExpressionData>> fTrustExpressionOperatorLessThanOrEqualThrowsRuntimeException = operator -> {
        throw new RuntimeException();
    };

    private static final F1<TrustExpressionOperatorGreaterThanOrEqual, TrustExpression<TrustExpressionData>> fTrustExpressionOperatorGreaterThanOrEqualThrowsRuntimeException = operator -> {
        throw new RuntimeException();
    };

    private static final F1<TrustExpressionOperatorGreaterThan, TrustExpression<TrustExpressionData>> fTrustExpressionOperatorGreaterThanThrowsRuntimeException = operator -> {
        throw new RuntimeException();
    };

    private static final F1<TrustExpressionOperatorEqual, TrustExpression<TrustExpressionData>> fTrustExpressionOperatorEqualThrowsRuntimeException = operator -> {
        throw new RuntimeException();
    };

    private static final F1<TrustExpressionOperatorNotEqual, TrustExpression<TrustExpressionData>> fTrustExpressionOperatorNotEqualThrowsRuntimeException = operator -> {
        throw new RuntimeException();
    };

    private static final F1<TrustExpressionOperatorContains, TrustExpression<TrustExpressionData>> fTrustExpressionOperatorContainsThrowsRuntimeException = operator -> {
        throw new RuntimeException();
    };

    private static final Parser<TrustExpression<TrustExpressionData>> parserExpressionOr =
            Parsers.sequence(
                    parserExpressionAndReference.lazy(),
                    Parsers.sequence(
                            parserTokenOperatorOr,
                            parserExpressionAndReference.lazy(),
                            P::p).many(),
                    (expression, expressionList) ->
                            iterableList(expressionList).foldLeft(expressionInner -> orExpression -> or(expressionInner, orExpression._2(), dataNonTerminal()), expression));

    private static final Parser<TrustExpression<TrustExpressionData>> parserExpressionAnd =
            Parsers.sequence(
                    parserExpressionNotReference.lazy(),
                    Parsers.sequence(
                            parserTokenOperatorAnd,
                            parserExpressionNotReference.lazy(),
                            P::p).many(),
                    (expression, expressionList) ->
                            iterableList(expressionList).foldLeft(expressionInner -> andExpression -> and(expressionInner, andExpression._2(), dataNonTerminal()), expression));

    private static final Parser<TrustExpression<TrustExpressionData>> parserExpressionNot =
            Parsers.sequence(
                    parserTokenOperatorNot.asOptional(),
                    parserExpressionRelationReference.lazy(),
                    (optional, expression) ->
                            optional.<TrustExpression<TrustExpressionData>>map(notString -> not(expression, dataNonTerminal())).orElse(expression));

    private static final Parser<TrustExpression<TrustExpressionData>> parserExpressionRelation =
            Parsers.sequence(
                    parserExpressionEqualityReference.lazy(),
                    Parsers.sequence(
                            Parsers.or(
                                    parserTokenOperatorGreaterThan,
                                    parserTokenOperatorLessThan,
                                    parserTokenOperatorGreaterThanOrEqual,
                                    parserTokenOperatorLessThanOrEqual)
                                    .map(lexerToken -> {
                                        final TrustExpressionOperatorBinary trustExpressionOperator =
                                                lexerToken == LexerTokenOperatorLessThan.OPERATOR_LESS_THAN ?
                                                        TrustExpressionOperatorLessThan.OPERATOR_LESS_THAN :
                                                        lexerToken == LexerTokenOperatorLessThanOrEqual.OPERATOR_LESS_THAN_OR_EQUAL ?
                                                                TrustExpressionOperatorLessThanOrEqual.OPERATOR_LESS_THAN_OR_EQUAL :
                                                                lexerToken == LexerTokenOperatorGreaterThanOrEqual.OPERATOR_GREATER_THAN_OR_EQUAL ?
                                                                        TrustExpressionOperatorGreaterThanOrEqual.OPERATOR_GREATER_THAN_OR_EQUAL :
                                                                        lexerToken == LexerTokenOperatorGreaterThan.OPERATOR_GREATER_THAN ?
                                                                                TrustExpressionOperatorGreaterThan.OPERATOR_GREATER_THAN :
                                                                                null;

                                        if (trustExpressionOperator != null) {
                                            return trustExpressionOperator;
                                        } else {
                                            throw new RuntimeException();
                                        }
                                    }),
                            parserExpressionEqualityReference.lazy(),
                            P::p).many(),
                    (expression, expressionList) ->
                            iterableList(expressionList).foldLeft(expressionInner -> expressionRest -> expressionRest._1().matchBinary(
                                    fTrustExpressionOperatorAndThrowsRuntimeException,
                                    fTrustExpressionOperatorOrThrowsRuntimeException,
                                    lessThan -> lessThan(expressionInner, expressionRest._2(), dataNonTerminal()),
                                    lessThanOrEqual -> lessThanOrEqual(expressionInner, expressionRest._2(), dataNonTerminal()),
                                    greaterThanOrEqual -> greaterThanOrEqual(expressionInner, expressionRest._2(), dataNonTerminal()),
                                    greaterThan -> greaterThan(expressionInner, expressionRest._2(), dataNonTerminal()),
                                    fTrustExpressionOperatorEqualThrowsRuntimeException,
                                    fTrustExpressionOperatorNotEqualThrowsRuntimeException,
                                    fTrustExpressionOperatorContainsThrowsRuntimeException), expression));


    private static final Parser<TrustExpression<TrustExpressionData>> parserExpressionEquality =
            Parsers.sequence(
                    parserExpressionAtomReference.lazy(),
                    Parsers.sequence(
                            Parsers.or(
                                    parserTokenOperatorEqual,
                                    parserTokenOperatorNotEqual)
                                    .map(lexerToken -> {
                                        final TrustExpressionOperatorBinary trustExpressionOperator =
                                                lexerToken == LexerTokenOperatorEqual.OPERATOR_EQUAL ?
                                                        TrustExpressionOperatorEqual.OPERATOR_EQUAL :
                                                        lexerToken == LexerTokenOperatorNotEqual.OPERATOR_NOT_EQUAL ?
                                                                TrustExpressionOperatorNotEqual.OPERATOR_NOT_EQUAL :
                                                                null;

                                        if (trustExpressionOperator != null) {
                                            return trustExpressionOperator;
                                        } else {
                                            throw new RuntimeException();
                                        }
                                    }),
                            parserExpressionAtomReference.lazy(),
                            P::p).many(),
                    (expression, expressionList) ->
                            iterableList(expressionList).foldLeft(expressionInner -> expressionRest -> expressionRest._1().matchBinary(
                                    fTrustExpressionOperatorAndThrowsRuntimeException,
                                    fTrustExpressionOperatorOrThrowsRuntimeException,
                                    fTrustExpressionOperatorLessThanThrowsRuntimeException,
                                    fTrustExpressionOperatorLessThanOrEqualThrowsRuntimeException,
                                    fTrustExpressionOperatorGreaterThanOrEqualThrowsRuntimeException,
                                    fTrustExpressionOperatorGreaterThanThrowsRuntimeException,
                                    equal -> equal(expressionInner, expressionRest._2(), dataNonTerminal()),
                                    notEqual -> notEqual(expressionInner, expressionRest._2(), dataNonTerminal()),
                                    fTrustExpressionOperatorContainsThrowsRuntimeException), expression));

    private static final Parser<TrustExpression<TrustExpressionData>> parserExpressionAtom =
            Parsers.or(
                    parserTokenLiteralString.map(token -> terminal(dataLiteralString(token.getValue().substring(1, token.getValue().length() - 1)))),
                    Parsers.sequence(
                            parserTokenIdentifier,
                            parserTokenSeparatorDot,
                            parserTokenIdentifier,
                            P::p).map(p -> terminal(dataReferenceTrustmarkDefinitionParameter(p._1().getValue(), p._3().getValue()))),
                    parserTokenIdentifier.map(tokenIdentifier -> terminal(dataReferenceTrustmarkDefinitionRequirement(tokenIdentifier.getValue()))),
                    Parsers.sequence(
                            parserTokenOperatorContains,
                            parserTokenSeparatorParenthesisLeft,
                            parserTokenIdentifier,
                            parserTokenSeparatorDot,
                            parserTokenIdentifier,
                            parserTokenSeparatorComma,
                            parserTokenLiteralString,
                            parserTokenSeparatorParenthesisRight,
                            P::p).map(p -> contains(terminal(dataReferenceTrustmarkDefinitionParameter(p._3().getValue(), p._5().getValue())), terminal(dataLiteralString(p._7().getValue().substring(1, p._7().getValue().length() - 1))), dataNonTerminal())),
                    Parsers.sequence(
                            parserTokenOperatorExists,
                            parserTokenSeparatorParenthesisLeft,
                            parserTokenIdentifier,
                            parserTokenSeparatorDot,
                            parserTokenIdentifier,
                            parserTokenSeparatorParenthesisRight,
                            P::p).map(p -> exists(terminal(dataReferenceTrustmarkDefinitionParameter(p._3().getValue(), p._5().getValue())), dataNonTerminal())),
                    parserTokenLiteralBooleanFalse.map(token -> terminal(dataLiteralBoolean(false))),
                    parserTokenLiteralBooleanTrue.map(token -> terminal(dataLiteralBoolean(true))),
                    parserTokenLiteralDateTimeStamp.map(token -> terminal(dataLiteralDateTimeStamp(Instant.parse(token.getValue())))),
                    parserTokenLiteralDecimal.map(token -> terminal(dataLiteralDecimal(new BigDecimal(token.getValue())))),
                    Parsers.sequence(
                            parserTokenSeparatorParenthesisLeft,
                            parserExpressionOrReference.lazy(),
                            parserTokenSeparatorParenthesisRight,
                            (parenthesisOpen, expression, parenthesisClose) -> expression));

    static {
        parserExpressionOrReference.set(parserExpressionOr);
        parserExpressionAndReference.set(parserExpressionAnd);
        parserExpressionNotReference.set(parserExpressionNot);
        parserExpressionRelationReference.set(parserExpressionRelation);
        parserExpressionEqualityReference.set(parserExpressionEquality);
        parserExpressionAtomReference.set(parserExpressionAtom);
    }

    public static Parser<TrustExpression<TrustExpressionData>> parser() {
        return parserExpressionOr.from(TrustExpressionLexerFactoryJParsec.lexer());
    }
}
