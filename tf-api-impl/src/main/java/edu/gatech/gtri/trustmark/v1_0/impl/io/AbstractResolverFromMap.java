package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import org.gtri.fj.function.Try1;

import java.net.URI;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public abstract class AbstractResolverFromMap<T0> extends AbstractResolver<T0> {

    private final Map<URI, T0> map;

    public AbstractResolverFromMap(
            final Try1<String, T0, ResolveException> deserializerForJson,
            final Try1<String, T0, ResolveException> deserializerForXML,
            final Try1<T0, T0, ResolveException> validator,
            final Map<URI, T0> map) {

        super(deserializerForJson, deserializerForXML, validator);

        requireNonNull(map);

        this.map = map;
    }

    @Override
    public T0 resolve(final URI uri, final Boolean validateNullable) throws ParseException {
        requireNonNull(uri);

        if (map.containsKey(uri)) {
            return map.get(uri);
        } else {
            throw new ParseException();
        }
    }
}
