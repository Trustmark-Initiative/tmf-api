package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.NamespaceContext;
import java.util.*;

/**
 * Created by brad on 1/7/16.
 */
public class DefaultNamespaceContext implements NamespaceContext {

    private static final Logger logger = LoggerFactory.getLogger(DefaultNamespaceContext.class);

    private int nsCounter = 1;
    Map<String, String> BY_PREFIX_CACHE = new HashMap<>();
    Map<String, String> BY_URI_CACHE = new HashMap<>();

    public DefaultNamespaceContext(){
        BY_PREFIX_CACHE.put(TrustmarkFrameworkConstants.NAMESPACE_PREFIX, TrustmarkFrameworkConstants.NAMESPACE_URI);
        BY_PREFIX_CACHE.put("ds", "http://www.w3.org/2000/09/xmldsig#");
        BY_PREFIX_CACHE.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");

        BY_URI_CACHE.put(TrustmarkFrameworkConstants.NAMESPACE_URI, TrustmarkFrameworkConstants.NAMESPACE_PREFIX);
        BY_URI_CACHE.put("http://www.w3.org/2000/09/xmldsig#", "ds");
        BY_URI_CACHE.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
    }


    @Override
    public String getNamespaceURI(String prefix) {
        String uri = BY_PREFIX_CACHE.get(prefix);

        // What to do if this is null?

        logger.debug("Returning uri["+uri+"] for prefix["+prefix+"]...");
        return uri;
    }

    @Override
    public String getPrefix(String namespaceURI) {
        String prefix = BY_URI_CACHE.get(namespaceURI);
        if( prefix == null ){
            prefix = "ns"+(nsCounter++);
            BY_PREFIX_CACHE.put(prefix, namespaceURI);
            BY_URI_CACHE.put(namespaceURI, prefix);
        }
        logger.debug("Returning prefix["+prefix+"] for uri["+namespaceURI+"]...");
        return prefix;
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
        List<String> prefixes = new ArrayList<>();
        if( this.getPrefix(namespaceURI) != null )
            prefixes.add(this.getPrefix(namespaceURI));
        return prefixes.iterator();
    }
}
