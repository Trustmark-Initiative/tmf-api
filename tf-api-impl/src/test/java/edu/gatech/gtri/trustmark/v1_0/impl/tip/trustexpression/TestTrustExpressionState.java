package edu.gatech.gtri.trustmark.v1_0.impl.tip.trustexpression;

import edu.gatech.gtri.trustmark.v1_0.tip.trustexpression.TrustExpressionState;
import org.junit.Test;

import static org.gtri.fj.function.Function.identity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestTrustExpressionState {

    @Test
    public void test() {
        assertThrows(NullPointerException.class, () -> TrustExpressionState.FAILURE.and(null));
        assertThrows(NullPointerException.class, () -> TrustExpressionState.FAILURE.or(null));
        assertThrows(NullPointerException.class, () -> TrustExpressionState.SUCCESS.match(null, identity(), identity(), identity()));
        assertThrows(NullPointerException.class, () -> TrustExpressionState.SUCCESS.match(identity(), null, identity(), identity()));
        assertThrows(NullPointerException.class, () -> TrustExpressionState.SUCCESS.match(identity(), identity(), null, identity()));
        assertThrows(NullPointerException.class, () -> TrustExpressionState.SUCCESS.match(identity(), identity(), identity(), null));

        assertEquals(TrustExpressionState.SUCCESS, TrustExpressionState.FAILURE.not());
        assertEquals(TrustExpressionState.FAILURE, TrustExpressionState.SUCCESS.not());
        assertEquals(TrustExpressionState.UNKNOWN, TrustExpressionState.UNKNOWN.not());

        assertEquals(TrustExpressionState.SUCCESS, TrustExpressionState.SUCCESS.and(TrustExpressionState.SUCCESS));
        assertEquals(TrustExpressionState.FAILURE, TrustExpressionState.SUCCESS.and(TrustExpressionState.FAILURE));
        assertEquals(TrustExpressionState.UNKNOWN, TrustExpressionState.SUCCESS.and(TrustExpressionState.UNKNOWN));
        assertEquals(TrustExpressionState.FAILURE, TrustExpressionState.FAILURE.and(TrustExpressionState.SUCCESS));
        assertEquals(TrustExpressionState.FAILURE, TrustExpressionState.FAILURE.and(TrustExpressionState.FAILURE));
        assertEquals(TrustExpressionState.FAILURE, TrustExpressionState.FAILURE.and(TrustExpressionState.UNKNOWN));
        assertEquals(TrustExpressionState.UNKNOWN, TrustExpressionState.UNKNOWN.and(TrustExpressionState.SUCCESS));
        assertEquals(TrustExpressionState.FAILURE, TrustExpressionState.UNKNOWN.and(TrustExpressionState.FAILURE));
        assertEquals(TrustExpressionState.UNKNOWN, TrustExpressionState.UNKNOWN.and(TrustExpressionState.UNKNOWN));

        assertEquals(TrustExpressionState.SUCCESS, TrustExpressionState.SUCCESS.or(TrustExpressionState.SUCCESS));
        assertEquals(TrustExpressionState.SUCCESS, TrustExpressionState.SUCCESS.or(TrustExpressionState.FAILURE));
        assertEquals(TrustExpressionState.SUCCESS, TrustExpressionState.SUCCESS.or(TrustExpressionState.UNKNOWN));
        assertEquals(TrustExpressionState.SUCCESS, TrustExpressionState.FAILURE.or(TrustExpressionState.SUCCESS));
        assertEquals(TrustExpressionState.FAILURE, TrustExpressionState.FAILURE.or(TrustExpressionState.FAILURE));
        assertEquals(TrustExpressionState.UNKNOWN, TrustExpressionState.FAILURE.or(TrustExpressionState.UNKNOWN));
        assertEquals(TrustExpressionState.SUCCESS, TrustExpressionState.UNKNOWN.or(TrustExpressionState.SUCCESS));
        assertEquals(TrustExpressionState.UNKNOWN, TrustExpressionState.UNKNOWN.or(TrustExpressionState.FAILURE));
        assertEquals(TrustExpressionState.UNKNOWN, TrustExpressionState.UNKNOWN.or(TrustExpressionState.UNKNOWN));

        assertEquals(1, TrustExpressionState.SUCCESS.<Integer>match(ignore -> 1, ignore -> 2, ignore -> 3, ignore -> 4));
        assertEquals(2, TrustExpressionState.FAILURE.<Integer>match(ignore -> 1, ignore -> 2, ignore -> 3, ignore -> 4));
        assertEquals(3, TrustExpressionState.UNKNOWN.<Integer>match(ignore -> 1, ignore -> 2, ignore -> 3, ignore -> 4));
    }
}
