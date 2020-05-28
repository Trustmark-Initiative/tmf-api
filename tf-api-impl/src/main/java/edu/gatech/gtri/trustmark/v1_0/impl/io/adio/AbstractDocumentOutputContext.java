package edu.gatech.gtri.trustmark.v1_0.impl.io.adio;

import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.util.Set;

/**
 * Created by Nicholas on 01/31/2017.
 */
public class AbstractDocumentOutputContext<W> {
    
    ////// Instance Fields //////
    public final W writer;
    protected final SetValuedMap<String, String> idResolutionMap = new HashSetValuedHashMap<>();
    
    ////// Constructor //////
    public AbstractDocumentOutputContext(W _writer) { this.writer = _writer; }
    
    ////// Instance Methods //////
    public Set<String> getEncounteredIdsForObjectName(String objectName) { return this.idResolutionMap.get(objectName); }
    
}
