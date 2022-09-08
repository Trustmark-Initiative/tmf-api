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
import org.jparsec.Parser;
import org.jparsec.Parsers;
import org.jparsec.Token;
import org.jparsec.pattern.Pattern;
import org.jparsec.pattern.Patterns;

import java.util.List;
import java.util.function.Function;

import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenLiteralAll.LITERAL_ALL;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenLiteralNone.LITERAL_NONE;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenOperatorAnd.OPERATOR_AND;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenOperatorNa.OPERATOR_NA;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenOperatorNo.OPERATOR_NO;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenOperatorNot.OPERATOR_NOT;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenOperatorOr.OPERATOR_OR;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenOperatorYes.OPERATOR_YES;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenSeparatorComma.SEPARATOR_COMMA;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenSeparatorEllipsis.SEPARATOR_ELLIPSIS;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenSeparatorParenthesisLeft.SEPARATOR_PARENTHESIS_LEFT;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.IssuanceCriteriaLexerToken.LexerTokenSeparatorParenthesisRight.SEPARATOR_PARENTHESIS_RIGHT;

public class IssuanceCriteriaLexerFactoryJParsec {

    private IssuanceCriteriaLexerFactoryJParsec() {
    }

    private static <T1 extends IssuanceCriteriaLexerToken> Parser<T1> parserStringCaseInsensitive(final T1 lexerToken) {
        return Patterns.stringCaseInsensitive(lexerToken.getValue()).toScanner("Scanner for " + lexerToken.getClass().getSimpleName()).source().map(string -> lexerToken);
    }

    private static <T1 extends IssuanceCriteriaLexerToken> Parser<T1> parser(final Pattern pattern, final Class<T1> lexerTokenClass, final Function<String, T1> lexerTokenFunction) {
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

    private static final Parser<Void> parserWhitespace =
            Patterns.isChar(Character::isWhitespace).many().toScanner("Scanner for whitespace");

    private static final Parser<LexerTokenOperatorOr> parserOperatorOr = parserStringCaseInsensitive(OPERATOR_OR);
    private static final Parser<LexerTokenOperatorAnd> parserOperatorAnd = parserStringCaseInsensitive(OPERATOR_AND);
    private static final Parser<LexerTokenOperatorNot> parserOperatorNot = parserStringCaseInsensitive(OPERATOR_NOT);
    private static final Parser<LexerTokenOperatorYes> parserOperatorYes = parserStringCaseInsensitive(OPERATOR_YES);
    private static final Parser<LexerTokenOperatorNo> parserOperatorNo = parserStringCaseInsensitive(OPERATOR_NO);
    private static final Parser<LexerTokenOperatorNa> parserOperatorNa = parserStringCaseInsensitive(OPERATOR_NA);
    private static final Parser<LexerTokenSeparatorParenthesisLeft> parserSeparatorParenthesisLeft = parserStringCaseInsensitive(SEPARATOR_PARENTHESIS_LEFT);
    private static final Parser<LexerTokenSeparatorParenthesisRight> parserSeparatorParenthesisRight = parserStringCaseInsensitive(SEPARATOR_PARENTHESIS_RIGHT);
    private static final Parser<LexerTokenSeparatorEllipsis> parserSeparatorEllipsis = parserStringCaseInsensitive(SEPARATOR_ELLIPSIS);
    private static final Parser<LexerTokenSeparatorComma> parserSeparatorComma = parserStringCaseInsensitive(SEPARATOR_COMMA);
    private static final Parser<LexerTokenLiteralAll> parserLiteralAll = parserStringCaseInsensitive(LITERAL_ALL);
    private static final Parser<LexerTokenLiteralNone> parserLiteralNone = parserStringCaseInsensitive(LITERAL_NONE);

    private static final Parser<LexerTokenIdentifier> parserIdentifier = parser(patternIdentifier, LexerTokenIdentifier.class, IssuanceCriteriaLexerToken::identifier);

    public static Parser<List<Token>> lexer() {
        return Parsers
                .longest(
                        parserLiteralAll,
                        parserLiteralNone,
                        parserOperatorAnd,
                        parserOperatorNot,
                        parserOperatorOr,
                        parserOperatorYes,
                        parserOperatorNo,
                        parserOperatorNa,
                        parserSeparatorEllipsis,
                        parserSeparatorComma,
                        parserSeparatorParenthesisLeft,
                        parserSeparatorParenthesisRight,
                        parserIdentifier
                )
                .lexer(
                        parserWhitespace);
    }
}
