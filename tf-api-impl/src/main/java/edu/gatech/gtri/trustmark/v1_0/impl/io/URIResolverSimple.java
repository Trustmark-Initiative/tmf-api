package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.URIResolver;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.net.*;

/**
 * A naive implementation of the URI resolver which does no caching and immediately retrieves each network resource
 * as requested by the system.  This version will only resolve URLs, local ids and URNs are not supported at all.
 * <br/><br/>
 * Created by brad on 12/8/15.
 */
public class URIResolverSimple extends AbstractURIResolver implements URIResolver {

    private static final Logger log = LogManager.getLogger(URIResolverSimple.class);

    @Override
    public String resolve(URI uri) throws ResolveException {
        URL url = null;
        try {
            url = uri.toURL();
        }catch(MalformedURLException murle){
            throw new ResolveException("URI is not a valid URL: "+uri+", the system does not support anything other than Internet-resolvable URLs.", murle);
        }
        try {
            return downloadUrl(url);
        }catch(IOException ioe){
            throw new ResolveException("IO Exception downloading the URL["+url+"]: "+ioe.getMessage(), ioe);
        }
    }//end resolve()



}//nd URIResolverSimple