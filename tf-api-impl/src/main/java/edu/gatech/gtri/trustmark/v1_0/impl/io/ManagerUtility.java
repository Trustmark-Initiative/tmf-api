package edu.gatech.gtri.trustmark.v1_0.impl.io;

import org.gtri.fj.data.List;

import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.List.list;
import static org.gtri.fj.data.List.nil;
import static org.gtri.fj.data.Option.fromNull;

public final class ManagerUtility {
    private ManagerUtility() {
    }

    public static List<Class> getClassAndAncestorList(
            final Class clazz) {
        requireNonNull(clazz);

        return list(clazz)
                .append(fromNull(clazz.getInterfaces())
                        .map(List::arrayList)
                        .orSome(nil()))
                .append(fromNull(clazz.getSuperclass())
                        .filter(superclass -> !superclass.equals(Object.class))
                        .map(ManagerUtility::getClassAndAncestorList)
                        .orSome(nil()));
    }
}
