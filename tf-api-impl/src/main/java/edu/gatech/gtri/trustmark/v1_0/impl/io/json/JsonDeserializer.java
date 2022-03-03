package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.io.ParseException;

import java.net.URI;

import static java.util.Objects.requireNonNull;

/**
 * Implementations deserialize JSON and, optionally, a URI into an artifact.
 *
 * @param <T1> the type of the artifact
 * @author GTRI Trustmark Team
 */
public interface JsonDeserializer<T1> {

    /**
     * Returns the artifact for the given JSON.
     *
     * @param string the JSON
     * @return the artifact
     * @throws NullPointerException if string is null
     * @throws ParseException       if an exception occurs during parsing
     */
    default T1 deserialize(final String string) throws ParseException {
        requireNonNull(string);

        return deserialize(string, null);
    }

    /**
     * Returns the artifact for the given JSON and URI.
     *
     * @param string the JSON
     * @param uri    the URI
     * @return the artifact
     * @throws ParseException if an exception occurs during parsing
     */
    T1 deserialize(final String string, final URI uri) throws ParseException;
}
