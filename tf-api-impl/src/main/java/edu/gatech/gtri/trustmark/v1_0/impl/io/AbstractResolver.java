package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import org.apache.commons.io.IOUtils;
import org.gtri.fj.data.List;
import org.gtri.fj.function.F1;
import org.gtri.fj.function.Try1;
import org.gtri.fj.function.Try2;
import org.gtri.fj.product.P2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static java.util.Objects.requireNonNull;

public abstract class AbstractResolver<T0> {

    private static final Logger log = LoggerFactory.getLogger(AbstractResolver.class);

    private final List<P2<F1<String, Boolean>, Try2<String, URI, T0, ResolveException>>> deserializerList;

    private final Try1<T0, T0, ResolveException> validator;

    public AbstractResolver(
            final List<P2<F1<String, Boolean>, Try2<String, URI, T0, ResolveException>>> deserializerList,
            final Try1<T0, T0, ResolveException> validator) {

        deserializerList.forEach(p -> {
            requireNonNull(p);
            requireNonNull(p._1());
            requireNonNull(p._2());
            requireNonNull(validator);
        });

        this.deserializerList = deserializerList;
        this.validator = validator;
    }

    public final T0 resolve(final URL url) throws ResolveException {
        requireNonNull(url);

        return resolve(url, false);
    }

    public final T0 resolve(final URL url, final Boolean validate) throws
            ResolveException {
        requireNonNull(url);

        try {
            return resolve(url.toURI(), validate);
        } catch (URISyntaxException uriSyntaxException) {
            throw new ResolveException(uriSyntaxException);
        }
    }

    public final T0 resolve(final URI uri) throws ResolveException {
        requireNonNull(uri);

        return resolve(uri, false);
    }

    public final T0 resolve(final File file) throws ResolveException {
        requireNonNull(file);

        return resolve(file, false);
    }

    public final T0 resolve(final File file, final Boolean validate) throws
            ResolveException {
        requireNonNull(file);

        FileReader reader = null;
        try {
            reader = new FileReader(file);
        } catch (IOException ioException) {
            throw new ResolveException(ioException);
        }
        return resolve(reader, validate);
    }

    public final T0 resolve(final InputStream inputStream) throws
            ResolveException {
        requireNonNull(inputStream);

        return resolve(inputStream, false);
    }

    public final T0 resolve(final InputStream inputStream,
                            final Boolean validate) throws ResolveException {
        requireNonNull(inputStream);

        return resolve(new InputStreamReader(inputStream), validate);
    }

    public final T0 resolve(final Reader reader) throws ResolveException {
        requireNonNull(reader);

        return resolve(reader, false);
    }

    public final T0 resolve(final Reader reader, final Boolean validate) throws
            ResolveException {
        requireNonNull(reader);

        StringWriter inMemoryString = new StringWriter();
        try {
            log.debug("Copying reader to string ...");
            IOUtils.copy(reader, inMemoryString);
            reader.close();
        } catch (IOException ioException) {
            throw new ResolveException(ioException);
        }
        return resolve(inMemoryString.toString(), validate);
    }

    public final T0 resolve(final String string) throws ResolveException {
        requireNonNull(string);

        return resolve(string, false);
    }

    public final T0 resolve(final String string,
                            final Boolean validateNullable) throws ResolveException {
        requireNonNull(string);

        return resolve(string, null, validateNullable);
    }

    public final T0 resolve(final String string, final URI uri, final Boolean validateNullable) throws ResolveException {

        requireNonNull(string);

        final boolean validate = validateNullable == null ? false : validateNullable;

        log.debug(validate ?
                "Reading and validating an entity from a string ..." :
                "Reading an entity from a string ...");

        if (log.isDebugEnabled())
            log.debug(string);

        T0 entity = null;

        for (P2<F1<String, Boolean>, Try2<String, URI, T0, ResolveException>> p : deserializerList.toCollection()) {
            if (p._1().f(string)) {
                log.debug("The system could deserialize the string.");
                entity = p._2().f(string, uri);
                break;
            }
        }

        if (entity == null) {
            throw new ParseException("The system could not deserialize the string.");
        }

        if (validate) {
            validator.f(entity);
        }

        return entity;

    }

    public abstract T0 resolve(URI uri, Boolean validate) throws
            ResolveException;
}
