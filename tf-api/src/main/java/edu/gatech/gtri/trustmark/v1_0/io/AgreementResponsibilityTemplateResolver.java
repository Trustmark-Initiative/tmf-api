package edu.gatech.gtri.trustmark.v1_0.io;

import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

/**
 * Can parse an Agreement Responsibility Template from various sources.
 * @author Nicholas Saney
 */
public interface AgreementResponsibilityTemplateResolver {
    
    public AgreementResponsibilityTemplate resolve(URL url) throws ResolveException;
    public AgreementResponsibilityTemplate resolve(URL url, Boolean validate) throws ResolveException;
    
    
    public AgreementResponsibilityTemplate resolve(URI uri) throws ResolveException;
    public AgreementResponsibilityTemplate resolve(URI uri, Boolean validate) throws ResolveException;
    
    
    public AgreementResponsibilityTemplate resolve(File file) throws ResolveException;
    public AgreementResponsibilityTemplate resolve(File file, Boolean validate) throws ResolveException;
    
    
    public AgreementResponsibilityTemplate resolve(InputStream inputStream) throws ResolveException;
    public AgreementResponsibilityTemplate resolve(InputStream inputStream, Boolean validate) throws ResolveException;
    
    
    public AgreementResponsibilityTemplate resolve(Reader reader) throws ResolveException;
    public AgreementResponsibilityTemplate resolve(Reader reader, Boolean validate) throws ResolveException;
    
    
    public AgreementResponsibilityTemplate resolve(String rawString) throws ResolveException;
    public AgreementResponsibilityTemplate resolve(String rawString, Boolean validate) throws ResolveException;
}
