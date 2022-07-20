package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import org.gtri.fj.data.Either;
import org.gtri.fj.data.List;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.F2;
import org.gtri.fj.function.F3;
import org.gtri.fj.function.F5;
import org.gtri.fj.function.F6;
import org.gtri.fj.function.Try;
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

    @Override
    public <T1> T1 resolve(
            final URI uri,
            final Boolean validateNullable,
            final F2<URI, T0, T1> onArtifactSuccess,
            final F3<URI, ResolveException, URI, T1> onContextSuccess,
            final F5<URI, ResolveException, URI, ResolveException, URI, T1> onServerSuccess,
            final F6<URI, ResolveException, URI, ResolveException, URI, ResolveException, T1> onServerFailure) {

        return Either.reduce(Try.f(() -> resolve(uri, validateNullable))._1()
                .map(artifact -> onArtifactSuccess.f(uri, artifact))
                .f().map(artifactException -> onServerFailure.f(uri, artifactException, uri, artifactException, uri, artifactException))
                .toEither());
    }
}
