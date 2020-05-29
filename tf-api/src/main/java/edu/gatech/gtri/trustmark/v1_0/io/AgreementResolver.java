package edu.gatech.gtri.trustmark.v1_0.io;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.Agreement;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

/**
 * Can parse an Agreement from various sources.
 * @author Nicholas Saney
 */
public interface AgreementResolver {
    
    public Agreement resolve(URL url) throws ResolveException;
    public Agreement resolve(URL url, Boolean validate) throws ResolveException;
    
    
    public Agreement resolve(URI uri) throws ResolveException;
    public Agreement resolve(URI uri, Boolean validate) throws ResolveException;
    
    
    public Agreement resolve(File file) throws ResolveException;
    public Agreement resolve(File file, Boolean validate) throws ResolveException;
    
    
    public Agreement resolve(InputStream inputStream) throws ResolveException;
    public Agreement resolve(InputStream inputStream, Boolean validate) throws ResolveException;
    
    
    public Agreement resolve(Reader reader) throws ResolveException;
    public Agreement resolve(Reader reader, Boolean validate) throws ResolveException;
    
    
    public Agreement resolve(String rawString) throws ResolveException;
    public Agreement resolve(String rawString, Boolean validate) throws ResolveException;
    
}
