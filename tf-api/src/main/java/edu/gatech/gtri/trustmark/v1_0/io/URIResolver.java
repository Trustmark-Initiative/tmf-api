package edu.gatech.gtri.trustmark.v1_0.io;

import java.net.URI;

/**
 * This interfaced is used by most parsers to resolve URIs.  This allows systems which work with URIs to implement
 * custom ways of resolving them.  For example, you could resolve URLs (subsets of URIs) by getting them off the
 * internet, or you could cache them in a database and return that based on some timeout mechanism.
 * <br/><br/>
 * Created by brad on 12/7/15.
 */
public interface URIResolver {

    public String resolve(URI uri) throws ResolveException;

    public String resolve(String uriString) throws ResolveException;

}// end URIResolver