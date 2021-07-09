package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression;
import org.gtri.fj.product.P;
import org.gtri.fj.product.Unit;
import org.jparsec.Parser;
import org.jparsec.Parsers;

import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenIdentifier;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenKeywordParenthesisLeft;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenKeywordParenthesisRight;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorAnd;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorNot;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorOr;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.and;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.not;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.or;
import static edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpression.terminal;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.product.Unit.unit;

public final class TrustExpressionParserFactoryJParsec {
    private TrustExpressionParserFactoryJParsec() {
    }

    private static final Parser<LexerTokenOperatorOr> parserTokenOperatorOr = Parsers.tokenType(LexerTokenOperatorOr.class, "Parser for " + LexerTokenOperatorOr.OPERATOR_OR.getValue());
    private static final Parser<LexerTokenOperatorAnd> parserTokenOperatorAnd = Parsers.tokenType(LexerTokenOperatorAnd.class, "Parser for " + LexerTokenOperatorAnd.OPERATOR_AND.getValue());
    private static final Parser<LexerTokenOperatorNot> parserTokenOperatorNot = Parsers.tokenType(LexerTokenOperatorNot.class, "Parser for " + LexerTokenOperatorNot.OPERATOR_NOT.getValue());
    private static final Parser<LexerTokenIdentifier> parserTokenIdentifier = Parsers.tokenType(LexerTokenIdentifier.class, "Parser for " + LexerTokenIdentifier.class.getSimpleName());
    private static final Parser<LexerTokenKeywordParenthesisLeft> parserTokenKeywordParenthesisLeft = Parsers.tokenType(LexerTokenKeywordParenthesisLeft.class, "Parser for " + LexerTokenKeywordParenthesisLeft.KEYWORD_PARENTHESIS_LEFT.getValue());
    private static final Parser<LexerTokenKeywordParenthesisRight> parserTokenKeywordParenthesisRight = Parsers.tokenType(LexerTokenKeywordParenthesisRight.class, "Parser for " + LexerTokenKeywordParenthesisRight.KEYWORD_PARENTHESIS_RIGHT.getValue());

    private static final Parser.Reference<TrustExpression<Unit, String>> parserExpressionOrReference = Parser.newReference();
    private static final Parser.Reference<TrustExpression<Unit, String>> parserExpressionAndReference = Parser.newReference();
    private static final Parser.Reference<TrustExpression<Unit, String>> parserExpressionNotReference = Parser.newReference();
    private static final Parser.Reference<TrustExpression<Unit, String>> parserExpressionAtomReference = Parser.newReference();

    private static final Parser<TrustExpression<Unit, String>> parserExpressionOr =
            Parsers.sequence(
                    parserExpressionAndReference.lazy(),
                    Parsers.sequence(
                            parserTokenOperatorOr,
                            parserExpressionAndReference.lazy(),
                            P::p).many(),
                    (expression, expressionList) ->
                            iterableList(expressionList).foldLeft(expressionInner -> orExpression -> or(expressionInner, orExpression._2(), unit()), expression));

    private static final Parser<TrustExpression<Unit, String>> parserExpressionAnd =
            Parsers.sequence(
                    parserExpressionNotReference.lazy(),
                    Parsers.sequence(
                            parserTokenOperatorAnd,
                            parserExpressionNotReference.lazy(),
                            P::p).many(),
                    (expression, expressionList) ->
                            iterableList(expressionList).foldLeft(expressionInner -> andExpression -> and(expressionInner, andExpression._2(), unit()), expression));

    private static final Parser<TrustExpression<Unit, String>> parserExpressionNot =
            Parsers.sequence(
                    parserTokenOperatorNot.asOptional(),
                    parserExpressionAtomReference.lazy(),
                    (notOptional, expression) ->
                            notOptional.<TrustExpression<Unit, String>>map(notString -> not(expression, unit())).orElse(expression));

    private static final Parser<TrustExpression<Unit, String>> parserExpressionAtom =
            Parsers.or(
                    parserTokenIdentifier.map(TokenOperatorNCName -> terminal(TokenOperatorNCName.getValue())),
                    Parsers.sequence(
                            parserTokenKeywordParenthesisLeft,
                            parserExpressionOrReference.lazy(),
                            parserTokenKeywordParenthesisRight,
                            (parenthesisOpen, expression, parenthesisClose) -> expression));

    static {
        parserExpressionOrReference.set(parserExpressionOr);
        parserExpressionAndReference.set(parserExpressionAnd);
        parserExpressionNotReference.set(parserExpressionNot);
        parserExpressionAtomReference.set(parserExpressionAtom);
    }

    public static Parser<TrustExpression<Unit, String>> parser() {
        return parserExpressionOr.from(TrustExpressionLexerFactoryJParsec.lexer());
    }
}
