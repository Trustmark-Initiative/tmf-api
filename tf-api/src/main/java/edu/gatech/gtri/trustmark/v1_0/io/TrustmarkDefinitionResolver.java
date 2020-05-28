package edu.gatech.gtri.trustmark.v1_0.io;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

import org.w3c.dom.Document;

import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;

/**
 * Can parse a Trustmark Definition from various sources.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface TrustmarkDefinitionResolver {

	public TrustmarkDefinition resolve(URL url) throws ResolveException;
	public TrustmarkDefinition resolve(URL url, Boolean validate) throws ResolveException;


	public TrustmarkDefinition resolve(URI uri) throws ResolveException;
	public TrustmarkDefinition resolve(URI uri, Boolean validate) throws ResolveException;


	public TrustmarkDefinition resolve(File file) throws ResolveException;
	public TrustmarkDefinition resolve(File file, Boolean validate) throws ResolveException;


	public TrustmarkDefinition resolve(InputStream tdInputStream) throws ResolveException;
	public TrustmarkDefinition resolve(InputStream tdInputStream, Boolean validate) throws ResolveException;


	public TrustmarkDefinition resolve(Reader tdReader) throws ResolveException;
	public TrustmarkDefinition resolve(Reader tdReader, Boolean validate) throws ResolveException;


	public TrustmarkDefinition resolve(String rawString) throws ResolveException;
	public TrustmarkDefinition resolve(String rawString, Boolean validate) throws ResolveException;

}
