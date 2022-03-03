package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import org.gtri.fj.data.List;
import org.gtri.fj.data.Option;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.Try1;
import org.gtri.fj.function.Try2;
import org.gtri.fj.product.P2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.gtri.fj.data.List.iteratorList;

public abstract class AbstractResolverFromURIResolver<T0> extends AbstractResolver<T0> {

    private static final Logger log = LoggerFactory.getLogger(AbstractResolverFromURIResolver.class);
    private final URIResolver uriResolver;
    private final URIIteratorStrategy uriIteratorStrategy;

    public AbstractResolverFromURIResolver(
            final List<P2<F1<String, Boolean>, Try2<String, URI, T0, ResolveException>>> deserializerList,
            final Try1<T0, T0, ResolveException> validator,
            final URIResolver uriResolver,
            final URIIteratorStrategy uriIteratorStrategy) {

        super(deserializerList, validator);

        requireNonNull(uriResolver);
        requireNonNull(uriIteratorStrategy);

        this.uriResolver = uriResolver;
        this.uriIteratorStrategy = uriIteratorStrategy;
    }

    @Override
    public final T0 resolve(final URI uri, final Boolean validateNullable) throws ResolveException {

        requireNonNull(uri);

        final boolean validate = validateNullable == null ? false : validateNullable;
        final Map<URI, ResolveException> map = new HashMap<>();

        for (final P2<URI, Option<String>> uriInner : iteratorList(uriIteratorStrategy.uriIterator(uri))) {
            try {

                log.debug(validate ?
                        "Resolving and validating entity URI (" + uriInner._1() + ") ..." :
                        "Resolving entity URI (" + uriInner._1() + ") ...");

                return uriInner._2().isSome() ?
                        resolve(uriResolver.resolve(uriInner._1(), uriInner._2().some()), uriInner._1(), validate) :
                        resolve(uriResolver.resolve(uriInner._1()), uriInner._1(), validate);

            } catch (final ResolveException resolveException) {

                map.put(uriInner._1(), resolveException);
            }
        }

        throw map.getOrDefault(uri, new ResolveException(format("The system could not resolve the URI ('%s').", uri)));
    }
}
