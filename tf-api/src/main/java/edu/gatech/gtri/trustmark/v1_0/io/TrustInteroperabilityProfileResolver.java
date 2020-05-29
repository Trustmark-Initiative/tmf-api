package edu.gatech.gtri.trustmark.v1_0.io;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

import org.w3c.dom.Document;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;

/**
 * Can parse a Trust Interoperability Profile from various sources.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface TrustInteroperabilityProfileResolver {

    /**
     * Parses the TIP from the given URL, without doing any validation.
     */
    public TrustInteroperabilityProfile resolve(URL tipURL) throws ResolveException;
    /**
     * Parses the TIP from the given URL, and validates according to the validate parameter (true = validate).
     */
    public TrustInteroperabilityProfile resolve(URL tipURL, Boolean validate) throws ResolveException;

    /**
     * Parses the TIP from the given URI, without doing any validation.
     */
    public TrustInteroperabilityProfile resolve(URI tipURI) throws ResolveException;
    /**
     * Parses the TIP from the given URI, and validates according to the validate parameter (true = validate).
     */
    public TrustInteroperabilityProfile resolve(URI tipURI, Boolean validate) throws ResolveException;

    /**
     * Parses the TIP from the given File, without doing any validation.
     */
    public TrustInteroperabilityProfile resolve(File tipFile) throws ResolveException;
    /**
     * Parses the TIP from the given File, and validates according to the validate parameter (true = validate).
     */
    public TrustInteroperabilityProfile resolve(File tipFile, Boolean validate) throws ResolveException;

    /**
     * Parses the TIP from the given InputStream, without doing any validation.
     */
	public TrustInteroperabilityProfile resolve(InputStream tipInputStream) throws ResolveException;
    /**
     * Parses the TIP from the given InputStream, and validates according to the validate parameter (true = validate).
     */
	public TrustInteroperabilityProfile resolve(InputStream tipInputStream, Boolean validate) throws ResolveException;

    /**
     * Parses the TIP from the given Reader, without doing any validation.
     */
	public TrustInteroperabilityProfile resolve(Reader tipReader) throws ResolveException;
    /**
     * Parses the TIP from the given Reader, and validates according to the validate parameter (true = validate).
     */
	public TrustInteroperabilityProfile resolve(Reader tipReader, Boolean validate) throws ResolveException;

    /**
     * Parses the TIP from the given raw String data, without doing any validation.
     */
	public TrustInteroperabilityProfile resolve(String rawTipString) throws ResolveException;
    /**
     * Parses the TIP from the given raw String data, and validates according to the validate parameter (true = validate).
     */
	public TrustInteroperabilityProfile resolve(String rawTipString, Boolean validate) throws ResolveException;

}
