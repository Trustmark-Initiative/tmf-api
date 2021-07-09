package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenIdentifier;
import org.junit.Test;

import java.util.ArrayList;

import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenKeywordParenthesisLeft.KEYWORD_PARENTHESIS_LEFT;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenKeywordParenthesisRight.KEYWORD_PARENTHESIS_RIGHT;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorAnd.OPERATOR_AND;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorNot.OPERATOR_NOT;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.LexerTokenOperatorOr.OPERATOR_OR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestTrustExpressionLexerFactoryJParsec {

    @Test
    public void test() {
        assertNotNull(TrustExpressionLexerFactoryJParsec.lexer());
        assertEquals(OPERATOR_OR, TrustExpressionLexerFactoryJParsec.lexer().parse(OPERATOR_OR.getValue()).iterator().next().value());
        assertEquals(OPERATOR_AND, TrustExpressionLexerFactoryJParsec.lexer().parse(OPERATOR_AND.getValue()).iterator().next().value());
        assertEquals(OPERATOR_NOT, TrustExpressionLexerFactoryJParsec.lexer().parse(OPERATOR_NOT.getValue()).iterator().next().value());
        assertEquals(KEYWORD_PARENTHESIS_LEFT, TrustExpressionLexerFactoryJParsec.lexer().parse(KEYWORD_PARENTHESIS_LEFT.getValue()).iterator().next().value());
        assertEquals(KEYWORD_PARENTHESIS_RIGHT, TrustExpressionLexerFactoryJParsec.lexer().parse(KEYWORD_PARENTHESIS_RIGHT.getValue()).iterator().next().value());
        assertEquals("identifier", ((LexerTokenIdentifier) TrustExpressionLexerFactoryJParsec.lexer().parse("identifier").iterator().next().value()).getValue());
        assertEquals(new ArrayList<>(), TrustExpressionLexerFactoryJParsec.lexer().parse("   "));
    }
}
