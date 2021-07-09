package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.gtri.fj.function.Try1;

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

    private static final Logger log = Logger.getLogger(AbstractResolver.class);

    private final Try1<String, T0, ResolveException> deserializerForJson;
    private final Try1<String, T0, ResolveException> deserializerForXML;
    private final Try1<T0, T0, ResolveException> validator;

    public AbstractResolver(
            final Try1<String, T0, ResolveException> deserializerForJson,
            final Try1<String, T0, ResolveException> deserializerForXML,
            final Try1<T0, T0, ResolveException> validator) {

        requireNonNull(deserializerForJson);
        requireNonNull(deserializerForXML);
        requireNonNull(validator);

        this.deserializerForJson = deserializerForJson;
        this.deserializerForXML = deserializerForXML;
        this.validator = validator;
    }

    public T0 resolve(final URL url) throws ResolveException {
        requireNonNull(url);

        return resolve(url, false);
    }

    public T0 resolve(final URL url, final Boolean validate) throws ResolveException {
        requireNonNull(url);

        try {
            return resolve(url.toURI(), validate);
        } catch (URISyntaxException uriSyntaxException) {
            throw new ResolveException(uriSyntaxException);
        }
    }

    public T0 resolve(final URI uri) throws ResolveException {
        requireNonNull(uri);

        return resolve(uri, false);
    }

    public T0 resolve(final File file) throws ResolveException {
        requireNonNull(file);

        return resolve(file, false);
    }

    public T0 resolve(final File file, final Boolean validate) throws ResolveException {
        requireNonNull(file);

        FileReader reader = null;
        try {
            reader = new FileReader(file);
        } catch (IOException ioException) {
            throw new ResolveException(ioException);
        }
        return resolve(reader, validate);
    }

    public T0 resolve(final InputStream inputStream) throws ResolveException {
        requireNonNull(inputStream);

        return resolve(inputStream, false);
    }

    public T0 resolve(final InputStream inputStream, final Boolean validate) throws ResolveException {
        requireNonNull(inputStream);

        return resolve(new InputStreamReader(inputStream), validate);
    }

    public T0 resolve(final Reader reader) throws ResolveException {
        requireNonNull(reader);

        return resolve(reader, false);
    }

    public T0 resolve(final Reader reader, final Boolean validate) throws ResolveException {
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

    public T0 resolve(final String string) throws ResolveException {
        requireNonNull(string);

        return resolve(string, false);
    }

    public T0 resolve(final String string, final Boolean validateNullable) throws ResolveException {
        requireNonNull(string);
        final boolean validate = validateNullable == null ? false : validateNullable;

        log.info(validate ?
                "Reading and validating an entity from a string ..." :
                "Reading an entity from a string ...");

        if (log.isDebugEnabled())
            log.debug(string);

        T0 entity = null;

        if (AbstractResolverUtility.isJson(string)) {
            log.debug("String is JSON, parsing ...");
            entity = deserializerForJson.f(string);
        } else if (AbstractResolverUtility.isXml(string)) {
            log.debug("String is XML, parsing ...");
            entity = deserializerForXML.f(string);
        } else {
            log.warn("String is neither JSON nor XML, not parsing ...");
            throw new ParseException("String is neither JSON nor XML.");
        }

        if (validate) {
            validator.f(entity);
        }

        return entity;
    }

    public abstract T0 resolve(URI uri, Boolean validate) throws ResolveException;
}
