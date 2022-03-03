package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import org.gtri.fj.data.List;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.Try1;
import org.gtri.fj.function.Try2;
import org.gtri.fj.product.P2;

import java.net.URI;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public abstract class AbstractResolverFromMap<T0> extends AbstractResolver<T0> {

    private final Map<URI, T0> map;

    public AbstractResolverFromMap(
            final List<P2<F1<String, Boolean>, Try2<String, URI, T0, ResolveException>>> deserializerList,
            final Try1<T0, T0, ResolveException> validator,
            final Map<URI, T0> map) {

        super(deserializerList, validator);

        requireNonNull(map);

        this.map = map;
    }

    @Override
    public final T0 resolve(final URI uri, final Boolean validateNullable) throws ResolveException {

        requireNonNull(uri);

        if (map.containsKey(uri)) {
            return map.get(uri);
        } else {
            throw new ResolveException(format("The system could not resolve the URI ('%s').", uri));
        }
    }
}
