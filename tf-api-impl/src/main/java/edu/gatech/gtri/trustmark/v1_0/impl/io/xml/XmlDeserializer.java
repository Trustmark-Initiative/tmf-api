package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.io.ParseException;

import java.net.URI;

import static java.util.Objects.requireNonNull;

/**
 * Implementations deserialize XML and, optionally, a URI into an artifact.
 *
 * @param <T1> the type of the artifact
 * @author GTRI Trustmark Team
 */
public interface XmlDeserializer<T1> {

    /**
     * Returns the artifact for the given XML.
     *
     * @param string the XML
     * @return the artifact
     * @throws NullPointerException if string is null
     * @throws ParseException       if an exception occurs during parsing
     */
    default T1 deserialize(final String string) throws ParseException {
        requireNonNull(string);

        return deserialize(string, null);
    }

    /**
     * Returns the artifact for the given XML and URI.
     *
     * @param string the XML
     * @param uri    the URI
     * @return the artifact
     * @throws ParseException if an exception occurs during parsing
     */
    T1 deserialize(final String string, final URI uri) throws ParseException;
}
