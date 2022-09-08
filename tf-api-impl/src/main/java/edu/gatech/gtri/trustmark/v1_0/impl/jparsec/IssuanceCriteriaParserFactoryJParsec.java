package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenIdentifier;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenLiteralAll;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenLiteralNone;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenOperatorAnd;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenOperatorNa;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenOperatorNo;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenOperatorNot;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenOperatorOr;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenOperatorYes;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenSeparatorComma;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenSeparatorEllipsis;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenSeparatorParenthesisLeft;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenSeparatorParenthesisRight;
import edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria;
import edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData;
import org.gtri.fj.product.P;
import org.jparsec.Parser;
import org.jparsec.Parsers;

import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.and;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.na;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.no;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.not;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.or;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.terminal;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteria.yes;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.dataLiteralAll;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.dataLiteralNone;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.dataNonTerminal;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.dataReferenceIdentifierList;
import static edu.gatech.gtri.trustmark.v1_0.issuanceCriteria.IssuanceCriteriaData.dataReferenceIdentifierRange;
import static java.lang.String.format;
import static org.gtri.fj.data.List.iterableList;
import static org.gtri.fj.data.NonEmptyList.nel;

public class IssuanceCriteriaParserFactoryJParsec {
    private IssuanceCriteriaParserFactoryJParsec() {
    }

    // @formatter:off
    private static final Parser<LexerTokenOperatorOr>                 parserTokenOperatorOr                  = Parsers.tokenType(LexerTokenOperatorOr.class,                  format("'%s'", LexerTokenOperatorOr.OPERATOR_OR.getValue()));     
    private static final Parser<LexerTokenOperatorAnd>                parserTokenOperatorAnd                 = Parsers.tokenType(LexerTokenOperatorAnd.class,                 format("'%s'", LexerTokenOperatorAnd.OPERATOR_AND.getValue()));       
    private static final Parser<LexerTokenOperatorNot>                parserTokenOperatorNot                 = Parsers.tokenType(LexerTokenOperatorNot.class,                 format("'%s'", LexerTokenOperatorNot.OPERATOR_NOT.getValue()));       
    private static final Parser<LexerTokenOperatorYes>                parserTokenOperatorYes                 = Parsers.tokenType(LexerTokenOperatorYes.class,                 format("'%s'", LexerTokenOperatorYes.OPERATOR_YES.getValue()));       
    private static final Parser<LexerTokenOperatorNo>                 parserTokenOperatorNo                  = Parsers.tokenType(LexerTokenOperatorNo.class,                  format("'%s'", LexerTokenOperatorNo.OPERATOR_NO.getValue()));     
    private static final Parser<LexerTokenOperatorNa>                 parserTokenOperatorNa                  = Parsers.tokenType(LexerTokenOperatorNa.class,                  format("'%s'", LexerTokenOperatorNa.OPERATOR_NA.getValue()));     
    private static final Parser<LexerTokenSeparatorParenthesisLeft>   parserTokenSeparatorParenthesisLeft    = Parsers.tokenType(LexerTokenSeparatorParenthesisLeft.class,    format("'%s'", LexerTokenSeparatorParenthesisLeft.SEPARATOR_PARENTHESIS_LEFT.getValue()));                                 
    private static final Parser<LexerTokenSeparatorParenthesisRight>  parserTokenSeparatorParenthesisRight   = Parsers.tokenType(LexerTokenSeparatorParenthesisRight.class,   format("'%s'", LexerTokenSeparatorParenthesisRight.SEPARATOR_PARENTHESIS_RIGHT.getValue()));                                   
    private static final Parser<LexerTokenSeparatorEllipsis>          parserTokenSeparatorEllipsis           = Parsers.tokenType(LexerTokenSeparatorEllipsis.class,           format("'%s'", LexerTokenSeparatorEllipsis.SEPARATOR_ELLIPSIS.getValue()));                   
    private static final Parser<LexerTokenSeparatorComma>             parserTokenSeparatorComma              = Parsers.tokenType(LexerTokenSeparatorComma.class,              format("'%s'", LexerTokenSeparatorComma.SEPARATOR_COMMA.getValue()));             
    private static final Parser<LexerTokenLiteralAll>                 parserTokenLiteralAll                  = Parsers.tokenType(LexerTokenLiteralAll.class,                  format("'%s'", LexerTokenLiteralAll.LITERAL_ALL.getValue()));     
    private static final Parser<LexerTokenLiteralNone>                parserTokenLiteralNone                 = Parsers.tokenType(LexerTokenLiteralNone.class,                 format("'%s'", LexerTokenLiteralNone.LITERAL_NONE.getValue()));
    private static final Parser<LexerTokenIdentifier>                 parserTokenIdentifier                  = Parsers.tokenType(LexerTokenIdentifier.class,                  "identifier");
    // @formatter:on

    private static final Parser.Reference<IssuanceCriteria<IssuanceCriteriaData>> parserExpressionOrReference = Parser.newReference();
    private static final Parser.Reference<IssuanceCriteria<IssuanceCriteriaData>> parserExpressionAndReference = Parser.newReference();
    private static final Parser.Reference<IssuanceCriteria<IssuanceCriteriaData>> parserExpressionNotReference = Parser.newReference();
    private static final Parser.Reference<IssuanceCriteria<IssuanceCriteriaData>> parserExpressionAtomReference = Parser.newReference();

    private static final Parser<IssuanceCriteria<IssuanceCriteriaData>> parserExpressionAll =
            Parsers.or(
                    Parsers.sequence(
                            parserTokenOperatorYes,
                            parserTokenSeparatorParenthesisLeft,
                            parserTokenLiteralAll,
                            parserTokenSeparatorParenthesisRight,
                            (operator, parenthesisOpen, all, parenthesisClose) -> yes(terminal(dataLiteralAll()), dataNonTerminal())),
                    Parsers.sequence(
                            parserTokenOperatorNo,
                            parserTokenSeparatorParenthesisLeft,
                            parserTokenLiteralAll,
                            parserTokenSeparatorParenthesisRight,
                            (operator, parenthesisOpen, all, parenthesisClose) -> no(terminal(dataLiteralAll()), dataNonTerminal())),
                    Parsers.sequence(
                            parserTokenOperatorNa,
                            parserTokenSeparatorParenthesisLeft,
                            parserTokenLiteralAll,
                            parserTokenSeparatorParenthesisRight,
                            (operator, parenthesisOpen, all, parenthesisClose) -> na(terminal(dataLiteralAll()), dataNonTerminal())),
                    Parsers.sequence(
                            parserTokenOperatorYes,
                            parserTokenSeparatorParenthesisLeft,
                            parserTokenLiteralNone,
                            parserTokenSeparatorParenthesisRight,
                            (operator, parenthesisOpen, none, parenthesisClose) -> yes(terminal(dataLiteralNone()), dataNonTerminal())),
                    Parsers.sequence(
                            parserTokenOperatorNo,
                            parserTokenSeparatorParenthesisLeft,
                            parserTokenLiteralNone,
                            parserTokenSeparatorParenthesisRight,
                            (operator, parenthesisOpen, none, parenthesisClose) -> no(terminal(dataLiteralNone()), dataNonTerminal())),
                    Parsers.sequence(
                            parserTokenOperatorNa,
                            parserTokenSeparatorParenthesisLeft,
                            parserTokenLiteralNone,
                            parserTokenSeparatorParenthesisRight,
                            (operator, parenthesisOpen, none, parenthesisClose) -> na(terminal(dataLiteralNone()), dataNonTerminal())),
                    parserExpressionOrReference.lazy());

    private static final Parser<IssuanceCriteria<IssuanceCriteriaData>> parserExpressionOr =
            Parsers.sequence(
                    parserExpressionAndReference.lazy(),
                    Parsers.sequence(
                            parserTokenOperatorOr,
                            parserExpressionAndReference.lazy(),
                            P::p).many(),
                    (expression, expressionList) ->
                            iterableList(expressionList).foldLeft(expressionInner -> orExpression -> or(expressionInner, orExpression._2(), dataNonTerminal()), expression));

    private static final Parser<IssuanceCriteria<IssuanceCriteriaData>> parserExpressionAnd =
            Parsers.sequence(
                    parserExpressionNotReference.lazy(),
                    Parsers.sequence(
                            parserTokenOperatorAnd,
                            parserExpressionNotReference.lazy(),
                            P::p).many(),
                    (expression, expressionList) ->
                            iterableList(expressionList).foldLeft(expressionInner -> andExpression -> and(expressionInner, andExpression._2(), dataNonTerminal()), expression));

    private static final Parser<IssuanceCriteria<IssuanceCriteriaData>> parserExpressionNot =
            Parsers.sequence(
                    parserTokenOperatorNot.asOptional(),
                    parserExpressionAtomReference.lazy(),
                    (optional, expression) ->
                            optional.<IssuanceCriteria<IssuanceCriteriaData>>map(notString -> not(expression, dataNonTerminal())).orElse(expression));

    private static final Parser<IssuanceCriteria<IssuanceCriteriaData>> parserExpressionAtom =
            Parsers.or(
                    Parsers.sequence(
                            parserTokenOperatorYes,
                            parserTokenSeparatorParenthesisLeft,
                            parserTokenIdentifier,
                            parserTokenSeparatorEllipsis,
                            parserTokenIdentifier,
                            parserTokenSeparatorParenthesisRight,
                            (operator, parenthesisOpen, identifierFrom, ellipsis, identifierTo, parenthesisClose) -> yes(terminal(dataReferenceIdentifierRange(identifierFrom.getValue(), identifierTo.getValue())), dataNonTerminal())),
                    Parsers.sequence(
                            parserTokenOperatorNo,
                            parserTokenSeparatorParenthesisLeft,
                            parserTokenIdentifier,
                            parserTokenSeparatorEllipsis,
                            parserTokenIdentifier,
                            parserTokenSeparatorParenthesisRight,
                            (operator, parenthesisOpen, identifierFrom, ellipsis, identifierTo, parenthesisClose) -> no(terminal(dataReferenceIdentifierRange(identifierFrom.getValue(), identifierTo.getValue())), dataNonTerminal())),
                    Parsers.sequence(
                            parserTokenOperatorNa,
                            parserTokenSeparatorParenthesisLeft,
                            parserTokenIdentifier,
                            parserTokenSeparatorEllipsis,
                            parserTokenIdentifier,
                            parserTokenSeparatorParenthesisRight,
                            (operator, parenthesisOpen, identifierFrom, ellipsis, identifierTo, parenthesisClose) -> na(terminal(dataReferenceIdentifierRange(identifierFrom.getValue(), identifierTo.getValue())), dataNonTerminal())),
                    Parsers.sequence(
                            parserTokenOperatorYes,
                            parserTokenSeparatorParenthesisLeft,
                            Parsers.sequence(
                                    parserTokenIdentifier,
                                    Parsers.sequence(
                                            parserTokenSeparatorComma,
                                            parserTokenIdentifier,
                                            (comma, identifier) -> identifier).many(), (identifier, identifierList) -> nel(identifier, iterableList(identifierList))),
                            parserTokenSeparatorParenthesisRight,
                            (operator, parenthesisOpen, identifierList, parenthesisClose) -> yes(terminal(dataReferenceIdentifierList(identifierList.map(LexerTokenIdentifier::getValue))), dataNonTerminal())),
                    Parsers.sequence(
                            parserTokenOperatorNo,
                            parserTokenSeparatorParenthesisLeft,
                            Parsers.sequence(
                                    parserTokenIdentifier,
                                    Parsers.sequence(
                                            parserTokenSeparatorComma,
                                            parserTokenIdentifier,
                                            (comma, identifier) -> identifier).many(), (identifier, identifierList) -> nel(identifier, iterableList(identifierList))),
                            parserTokenSeparatorParenthesisRight,
                            (operator, parenthesisOpen, identifierList, parenthesisClose) -> no(terminal(dataReferenceIdentifierList(identifierList.map(LexerTokenIdentifier::getValue))), dataNonTerminal())),
                    Parsers.sequence(
                            parserTokenOperatorNa,
                            parserTokenSeparatorParenthesisLeft,
                            Parsers.sequence(
                                    parserTokenIdentifier,
                                    Parsers.sequence(
                                            parserTokenSeparatorComma,
                                            parserTokenIdentifier,
                                            (comma, identifier) -> identifier).many(), (identifier, identifierList) -> nel(identifier, iterableList(identifierList))),
                            parserTokenSeparatorParenthesisRight,
                            (operator, parenthesisOpen, identifierList, parenthesisClose) -> na(terminal(dataReferenceIdentifierList(identifierList.map(LexerTokenIdentifier::getValue))), dataNonTerminal())),
                    Parsers.sequence(
                            parserTokenSeparatorParenthesisLeft,
                            parserExpressionOrReference.lazy(),
                            parserTokenSeparatorParenthesisRight,
                            (parenthesisOpen, expression, parenthesisClose) -> expression));

    static {
        parserExpressionOrReference.set(parserExpressionOr);
        parserExpressionAndReference.set(parserExpressionAnd);
        parserExpressionNotReference.set(parserExpressionNot);
        parserExpressionAtomReference.set(parserExpressionAtom);
    }

    public static Parser<IssuanceCriteria<IssuanceCriteriaData>> parser() {
        return parserExpressionAll.from(IssuanceCriteriaLexerFactoryJParsec.lexer());
    }
}
