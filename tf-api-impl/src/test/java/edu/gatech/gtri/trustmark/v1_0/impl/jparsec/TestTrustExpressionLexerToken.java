package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import org.junit.jupiter.api.Test;

import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.identifier;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.literalDateTimeStamp;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.literalDecimal;
import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.literalString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTrustExpressionLexerToken {

    @Test
    public void test() {

        final String string = "";

        assertThrows(NullPointerException.class, () -> identifier(null));
        assertThrows(NullPointerException.class, () -> literalDateTimeStamp(null));
        assertThrows(NullPointerException.class, () -> literalDecimal(null));
        assertThrows(NullPointerException.class, () -> literalString(null));
        assertEquals(string, identifier(string).getValue());
        assertEquals(string, literalDateTimeStamp(string).getValue());
        assertEquals(string, literalDecimal(string).getValue());
        assertEquals(string, literalString(string).getValue());
    }
}
