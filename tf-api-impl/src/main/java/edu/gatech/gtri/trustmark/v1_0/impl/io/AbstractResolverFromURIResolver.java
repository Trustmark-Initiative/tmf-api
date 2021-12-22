package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.gtri.fj.function.Try1;

import java.net.URI;

import static java.util.Objects.requireNonNull;

public abstract class AbstractResolverFromURIResolver<T0> extends AbstractResolver<T0> {

    private static final Logger log = LoggerFactory.getLogger(AbstractResolverFromURIResolver.class);
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

        if (uri.getQuery() != null) {

            log.debug(validate ?
                    "Resolving and validating entity URI (" + uri + ") ..." :
                    "Resolving entity URI (" + uri + ") ...");

            return resolve(uriResolver.resolve(uri), validate);

        } else {
            try {

                log.debug(validate ?
                        "Resolving and validating entity URI (" + uri + "?format=xml) ..." :
                        "Resolving entity URI (" + uri + "?format=xml) ...");

                return resolve(uriResolver.resolve(uri + "?format=xml"), validate);

            } catch (final ResolveException resolveException) {

                log.debug(validate ?
                        "Resolving and validating entity URI (" + uri + ") ..." :
                        "Resolving entity URI (" + uri + ") ...");

                return resolve(uriResolver.resolve(uri), validate);

            }
        }
    }
}
