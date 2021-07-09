package edu.gatech.gtri.trustmark.v1_0.impl.jparsec;

import org.junit.Test;

import static edu.gatech.gtri.trustmark.v1_0.impl.jparsec.TrustExpressionLexerToken.identifier;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTrustExpressionLexerToken {

    @Test
    public void test() {

        final String identifier = "";

        assertThrows(NullPointerException.class, () -> identifier(null));
        assertEquals(identifier, identifier(identifier).getValue());
    }
}
