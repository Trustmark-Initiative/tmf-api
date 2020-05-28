package edu.gatech.gtri.trustmark.v1_0.io;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

import org.w3c.dom.Document;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;

/**
 * Can parse a trustmark from various sources.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface TrustmarkResolver {


    public Trustmark resolve(URL url) throws ResolveException;
    public Trustmark resolve(URL url, Boolean validate) throws ResolveException;

    public Trustmark resolve(URI uri) throws ResolveException;
    public Trustmark resolve(URI uri, Boolean validate) throws ResolveException;


    public Trustmark resolve(File file) throws ResolveException;
    public Trustmark resolve(File file, Boolean validate) throws ResolveException;


    public Trustmark resolve(InputStream trustmarkInputStream) throws ResolveException;
	public Trustmark resolve(InputStream trustmarkInputStream, Boolean validate) throws ResolveException;


	public Trustmark resolve(Reader trustmarkReader) throws ResolveException;
	public Trustmark resolve(Reader trustmarkReader, Boolean validate) throws ResolveException;


	public Trustmark resolve(String utf8EncodedTrustmarkString) throws ResolveException;
	public Trustmark resolve(String utf8EncodedTrustmarkString, Boolean validate) throws ResolveException;

}
