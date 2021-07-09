package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import org.apache.log4j.Logger;
import org.gtri.fj.function.Try1;

import java.net.URI;

import static java.util.Objects.requireNonNull;

public abstract class AbstractResolverFromURIResolver<T0> extends AbstractResolver<T0> {

    private static final Logger log = Logger.getLogger(AbstractResolverFromURIResolver.class);
    private final URIResolver uriResolver;

    public AbstractResolverFromURIResolver(
            final Try1<String, T0, ResolveException> deserializerForJson,
            final Try1<String, T0, ResolveException> deserializerForXML,
            final Try1<T0, T0, ResolveException> validator,
            final URIResolver uriResolver) {

        super(deserializerForJson, deserializerForXML, validator);

        requireNonNull(uriResolver);

        this.uriResolver = uriResolver;
    }

    @Override
    public T0 resolve(final URI uri, final Boolean validateNullable) throws ResolveException {
        requireNonNull(uri);

        final boolean validate = validateNullable == null ? false : validateNullable;

        log.info(validate ?
                "Resolving and validating entity URI (" + uri + ") ..." :
                "Resolving entity URI (" + uri + ") ...");

        try {
            log.debug("Resolving URI (" + uri + "?format=xml) ...");
            return resolve(uriResolver.resolve(uri + "?format=xml"), validate);
        } catch (final ResolveException resolveException) {
            log.debug("Resolving URI (" + uri + ") ...");
            return resolve(uriResolver.resolve(uri), validate);
        }
    }
}
