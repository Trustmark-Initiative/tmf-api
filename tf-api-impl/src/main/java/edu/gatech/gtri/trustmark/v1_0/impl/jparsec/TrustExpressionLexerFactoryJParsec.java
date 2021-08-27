package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenIdentifier;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenLiteralBooleanFalse;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenLiteralBooleanTrue;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenLiteralDateTimeStamp;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenLiteralDecimal;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenLiteralString;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorAnd;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorContains;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorEqual;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorExists;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorGreaterThan;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorGreaterThanOrEqual;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorLessThan;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorLessThanOrEqual;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorNot;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorNotEqual;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorOr;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenSeparatorComma;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenSeparatorDot;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenSeparatorParenthesisLeft;
import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenSeparatorParenthesisRight;
import org.jparsec.Parser;
import org.jparsec.Parsers;
import org.jparsec.Token;
import org.jparsec.pattern.Pattern;
import org.jparsec.pattern.Patterns;

import java.util.List;
import java.util.function.Function;

import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenLiteralBooleanFalse.LITERAL_BOOLEAN_FALSE;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenLiteralBooleanTrue.LITERAL_BOOLEAN_TRUE;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorAnd.OPERATOR_AND;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorContains.OPERATOR_CONTAINS;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorEqual.OPERATOR_EQUAL;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorExists.OPERATOR_EXISTS;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorGreaterThan.OPERATOR_GREATER_THAN;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorGreaterThanOrEqual.OPERATOR_GREATER_THAN_OR_EQUAL;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorLessThan.OPERATOR_LESS_THAN;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorLessThanOrEqual.OPERATOR_LESS_THAN_OR_EQUAL;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorNot.OPERATOR_NOT;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorNotEqual.OPERATOR_NOT_EQUAL;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorOr.OPERATOR_OR;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenSeparatorComma.SEPARATOR_COMMA;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenSeparatorDot.SEPARATOR_DOT;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenSeparatorParenthesisLeft.SEPARATOR_PARENTHESIS_LEFT;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenSeparatorParenthesisRight.SEPARATOR_PARENTHESIS_RIGHT;

public final class TrustExpressionLexerFactoryJParsec {

    private TrustExpressionLexerFactoryJParsec() {
    }

    private static <T1 extends TrustExpressionLexerToken> Parser<T1> parserStringCaseInsensitive(final T1 lexerToken) {
        return Patterns.stringCaseInsensitive(lexerToken.getValue()).toScanner("Scanner for " + lexerToken.getClass().getSimpleName()).source().map(string -> lexerToken);
    }

    private static <T1 extends TrustExpressionLexerToken> Parser<T1> parserString(final T1 lexerToken) {
        return Patterns.string(lexerToken.getValue()).toScanner("Scanner for " + lexerToken.getClass().getSimpleName()).source().map(string -> lexerToken);
    }

    private static <T1 extends TrustExpressionLexerToken> Parser<T1> parser(final Pattern pattern, final Class<T1> lexerTokenClass, final Function<String, T1> lexerTokenFunction) {
        return pattern.toScanner("Scanner for " + lexerTokenClass.getSimpleName()).source().map(lexerTokenFunction);
    }

    // see https://trustmarkinitiative.org/specifications/trustmark-framework/1.4/schema/tf.xsd
    // id

    private static final Pattern patternIdentifier =
            Patterns.sequence(
                    Patterns.or(
                            Patterns.isChar('_'),
                            Patterns.range('A', 'Z'),
                            Patterns.range('a', 'z')),
                    Patterns.or(
                            Patterns.isChar('_'),
                            Patterns.range('A', 'Z'),
                            Patterns.range('a', 'z'),
                            Patterns.range('0', '9'),
                            Patterns.isChar('-')).many());

    // see https://www.w3.org/TR/xmlschema11-2/#dateTimeStamp
    // (\+|-)?([0-9]+(\.[0-9]*)?|\.[0-9]+)

    private static final Pattern patternDateTimeStamp =
            Patterns.sequence(
                    Patterns.isChar('-').optional(),
                    Patterns.or(
                            Patterns.sequence(
                                    Patterns.range('1', '9'),
                                    Patterns.range('0', '9').atLeast(3)),
                            Patterns.sequence(
                                    Patterns.isChar('0'),
                                    Patterns.range('0', '9').times(3))),
                    Patterns.isChar('-'),
                    Patterns.or(
                            Patterns.sequence(
                                    Patterns.isChar('0'),
                                    Patterns.range('1', '9')),
                            Patterns.sequence(
                                    Patterns.isChar('1'),
                                    Patterns.range('0', '2'))),
                    Patterns.isChar('-'),
                    Patterns.or(
                            Patterns.sequence(
                                    Patterns.isChar('0'),
                                    Patterns.range('1', '9')),
                            Patterns.sequence(
                                    Patterns.range('1', '2'),
                                    Patterns.range('0', '9')),
                            Patterns.sequence(
                                    Patterns.isChar('3'),
                                    Patterns.range('0', '1'))),
                    Patterns.isChar('T'),
                    Patterns.or(
                            Patterns.sequence(
                                    Patterns.or(
                                            Patterns.sequence(
                                                    Patterns.range('0', '1'),
                                                    Patterns.range('0', '9')),
                                            Patterns.sequence(
                                                    Patterns.isChar('2'),
                                                    Patterns.range('0', '3'))),
                                    Patterns.isChar(':'),
                                    Patterns.range('0', '5'),
                                    Patterns.range('0', '9'),
                                    Patterns.isChar(':'),
                                    Patterns.range('0', '5'),
                                    Patterns.range('0', '9'),
                                    Patterns.sequence(
                                            Patterns.isChar('.'),
                                            Patterns.range('0', '9').atLeast(1)).optional()),
                            Patterns.sequence(
                                    Patterns.string("24:00:00"),
                                    Patterns.sequence(
                                            Patterns.isChar('.'),
                                            Patterns.isChar('0').atLeast(1)).optional())),
                    Patterns.or(
                            Patterns.isChar('Z'),
                            Patterns.sequence(
                                    Patterns.or(
                                            Patterns.isChar('+'),
                                            Patterns.isChar('-')),
                                    Patterns.or(
                                            Patterns.sequence(
                                                    Patterns.or(
                                                            Patterns.sequence(
                                                                    Patterns.isChar('0'),
                                                                    Patterns.range('0', '9')),
                                                            Patterns.sequence(
                                                                    Patterns.isChar('1'),
                                                                    Patterns.range('0', '3'))),
                                                    Patterns.isChar(':'),
                                                    Patterns.range('0', '5'),
                                                    Patterns.range('0', '9')),
                                            Patterns.string("14:00")))));

    // see https://www.w3.org/TR/xmlschema11-2/#decimal
    // (\+|-)?([0-9]+(\.[0-9]*)?|\.[0-9]+)

    private static final Pattern patternDecimal =
            Patterns.sequence(
                    Patterns.or(
                            Patterns.isChar('+'),
                            Patterns.isChar('-')).optional(),
                    Patterns.or(
                            Patterns.sequence(
                                    Patterns.range('0', '9').many1(),
                                    Patterns.sequence(
                                            Patterns.isChar('.'),
                                            Patterns.range('0', '9').many()).optional()),
                            Patterns.sequence(
                                    Patterns.isChar('.'),
                                    Patterns.range('0', '9').many1())));

    private static final Pattern patternString =
            Patterns.sequence(
                    Patterns.isChar('\''),
                    Patterns.isChar(c -> c != '\'').many(),
                    Patterns.isChar('\''));

    private static final Parser<Void> parserWhitespace =
            Patterns.isChar(Character::isWhitespace).many().toScanner("Scanner for whitespace");

    private static final Parser<LexerTokenOperatorAnd> parserOperatorAnd = parserStringCaseInsensitive(OPERATOR_AND);
    private static final Parser<LexerTokenOperatorNot> parserOperatorNot = parserStringCaseInsensitive(OPERATOR_NOT);
    private static final Parser<LexerTokenOperatorOr> parserOperatorOr = parserStringCaseInsensitive(OPERATOR_OR);

    private static final Parser<LexerTokenOperatorGreaterThan> parserOperatorGreaterThan = parserString(OPERATOR_GREATER_THAN);
    private static final Parser<LexerTokenOperatorGreaterThanOrEqual> parserOperatorGreaterThanOrEqual = parserString(OPERATOR_GREATER_THAN_OR_EQUAL);
    private static final Parser<LexerTokenOperatorLessThan> parserOperatorLessThan = parserString(OPERATOR_LESS_THAN);
    private static final Parser<LexerTokenOperatorLessThanOrEqual> parserOperatorLessThanOrEqual = parserString(OPERATOR_LESS_THAN_OR_EQUAL);

    private static final Parser<LexerTokenOperatorEqual> parserOperatorEqual = parserString(OPERATOR_EQUAL);
    private static final Parser<LexerTokenOperatorNotEqual> parserOperatorNotEqual = parserString(OPERATOR_NOT_EQUAL);

    private static final Parser<LexerTokenOperatorContains> parserOperatorContains = parserString(OPERATOR_CONTAINS);
    private static final Parser<LexerTokenOperatorExists> parserOperatorExists = parserString(OPERATOR_EXISTS);

    private static final Parser<LexerTokenSeparatorComma> parserSeparatorComma = parserString(SEPARATOR_COMMA);
    private static final Parser<LexerTokenSeparatorDot> parserSeparatorDot = parserString(SEPARATOR_DOT);
    private static final Parser<LexerTokenSeparatorParenthesisLeft> parserSeparatorParenthesisLeft = parserString(SEPARATOR_PARENTHESIS_LEFT);
    private static final Parser<LexerTokenSeparatorParenthesisRight> parserSeparatorParenthesisRight = parserString(SEPARATOR_PARENTHESIS_RIGHT);

    private static final Parser<LexerTokenLiteralBooleanFalse> parserLiteralBooleanFalse = parserString(LITERAL_BOOLEAN_FALSE);
    private static final Parser<LexerTokenLiteralBooleanTrue> parserLiteralBooleanTrue = parserString(LITERAL_BOOLEAN_TRUE);

    private static final Parser<LexerTokenIdentifier> parserIdentifier = parser(patternIdentifier, LexerTokenIdentifier.class, TrustExpressionLexerToken::identifier);

    private static final Parser<LexerTokenLiteralDateTimeStamp> parserLiteralDateTimeStamp = parser(patternDateTimeStamp, LexerTokenLiteralDateTimeStamp.class, TrustExpressionLexerToken::literalDateTimeStamp);
    private static final Parser<LexerTokenLiteralDecimal> parserLiteralDecimal = parser(patternDecimal, LexerTokenLiteralDecimal.class, TrustExpressionLexerToken::literalDecimal);
    private static final Parser<LexerTokenLiteralString> parserLiteralString = parser(patternString, LexerTokenLiteralString.class, TrustExpressionLexerToken::literalString);

    public static Parser<List<Token>> lexer() {
        return Parsers
                .longest(
                        parserLiteralBooleanFalse,
                        parserLiteralBooleanTrue,
                        parserLiteralDateTimeStamp,
                        parserLiteralDecimal,
                        parserLiteralString,
                        parserOperatorAnd,
                        parserOperatorContains,
                        parserOperatorEqual,
                        parserOperatorExists,
                        parserOperatorGreaterThan,
                        parserOperatorGreaterThanOrEqual,
                        parserOperatorLessThan,
                        parserOperatorLessThanOrEqual,
                        parserOperatorNot,
                        parserOperatorNotEqual,
                        parserOperatorOr,
                        parserSeparatorComma,
                        parserSeparatorDot,
                        parserSeparatorParenthesisLeft,
                        parserSeparatorParenthesisRight,
                        parserIdentifier
                )
                .lexer(
                        parserWhitespace);
    }
}
