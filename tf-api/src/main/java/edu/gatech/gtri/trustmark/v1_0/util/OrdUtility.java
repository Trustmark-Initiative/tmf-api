package edu.gatech.gtri.trustmark.v1_0.util;

import org.gtri.fj.Ord;
import org.gtri.fj.Ordering;

import static org.gtri.fj.Ord.ord;

public class OrdUtility {

    public static <T1> Ord<T1> nullableOrd(final Ord<T1> ord) {
        return ord((o1, o2) -> o1 == null && o2 == null ?
                Ordering.EQ :
                o1 == null ?
                        Ordering.LT :
                        o2 == null ?
                                Ordering.GT :
                                ord.compare(o1, o2));

    }

    public static <T1> Ord<T1> priorityOrd(
            final Ord<T1> ord1,
            final Ord<T1> ord2) {
        return ord((o1, o2) -> ord1.compare(o1, o2) != Ordering.EQ ? ord1.compare(o1, o2) : ord2.compare(o1, o2));
    }
}
