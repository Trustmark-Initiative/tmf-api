package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.*;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.dom4j.QName;

import java.util.*;

/**
 * Created by Nicholas on 01/25/2017.
 */
public class AdioTest1ElementNode2Codec extends Codec<AdioTest1.ElementNode2> {
    @Override
    public Class<AdioTest1.ElementNode2> getSupportedType() { return AdioTest1.ElementNode2.class; }
    
    @Override
    public Map<String, QName> getNonTfQualifiedNamesByElementName() { return null; }
    
    @Override
    public SetValuedMap<String, String> getAttributeNamesByElementName() { return null; }
    
    @Override
    public BidiMap<String, Class<?>> getObjectSequenceParentNameChildTypeMap() { return null; }
    
    @Override
    public BidiMap<String, String> getValueSequenceParentNameChildNameMap() { return null; }
    
    @Override
    public Set<String> getCdataValueNames() {
        return new LinkedHashSet<>(Arrays.asList(
            "ChildCdataValueNode3",
            "ChildCdataValueNode4"
        ));
    }
    
    @Override
    public String getIdFor(AdioTest1.ElementNode2 obj) { return obj.identifier; }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(
        AdioTest1.ElementNode2 obj,
        AbstractDocumentOutputSerializer<AdioTest1.ElementNode2, W, Ex> ados,
        AbstractDocumentOutputContext<W> context
    )
        throws Ex
    {
        ados.serializeValueInObject(context, "ChildNonCdataValueNode3", obj.childNonCdataValueNode3);
        ados.serializeValueInObject(context, "ChildNonCdataValueNode4", obj.childNonCdataValueNode4);
        ados.serializeValueInObject(context, "ChildCdataValueNode3", obj.childCdataValueNode3);
        ados.serializeValueInObject(context, "ChildCdataValueNode4", obj.childCdataValueNode4);
    }
    
    @Override
    public <O> AdioTest1.ElementNode2 deserializeFullObject(
        O node,
        String id,
        String sourceString,
        String sourceType,
        AbstractDocumentInputDeserializer<AdioTest1.ElementNode2, O, ?, ?> adid,
        AbstractDocumentInputContext context
    ) throws ParseException
    {
        AdioTest1.ElementNode2 result = new AdioTest1.ElementNode2();
        result.identifier = id;
        result.childNonCdataValueNode3 = adid.deserializeStringValueInObject(node, true, "ChildNonCdataValueNode3");
        result.childNonCdataValueNode4 = adid.deserializeStringValueInObject(node, true, "ChildNonCdataValueNode4");
        result.childCdataValueNode3 = adid.deserializeStringValueInObject(node, true, "ChildCdataValueNode3");
        result.childCdataValueNode4 = adid.deserializeStringValueInObject(node, true, "ChildCdataValueNode4");
        return result;
    }
    
}
