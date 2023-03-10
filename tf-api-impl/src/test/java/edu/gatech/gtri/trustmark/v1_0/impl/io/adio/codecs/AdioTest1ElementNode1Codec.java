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
public class AdioTest1ElementNode1Codec extends Codec<AdioTest1.ElementNode1> {
    @Override
    public Class<AdioTest1.ElementNode1> getSupportedType() { return AdioTest1.ElementNode1.class; }
    
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
            "ChildCdataValueNode1",
            "ChildCdataValueNode2"
        ));
    }
    
    @Override
    public String getIdFor(AdioTest1.ElementNode1 obj) { return null; }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(
        AdioTest1.ElementNode1 obj,
        AbstractDocumentOutputSerializer<AdioTest1.ElementNode1, W, Ex> ados,
        AbstractDocumentOutputContext<W> context
    )
        throws Ex
    {
        ados.serializeValueInObject(context, "ChildNonCdataValueNode1", obj.childNonCdataValueNode1);
        ados.serializeValueInObject(context, "ChildNonCdataValueNode2", obj.childNonCdataValueNode2);
        ados.serializeValueInObject(context, "ChildCdataValueNode1", obj.childCdataValueNode1);
        ados.serializeValueInObject(context, "ChildCdataValueNode2", obj.childCdataValueNode2);
    }
    
    @Override
    public <O> AdioTest1.ElementNode1 deserializeFullObject(
        O node,
        String id,
        String sourceString,
        String sourceType,
        AbstractDocumentInputDeserializer<AdioTest1.ElementNode1, O, ?, ?> adid,
        AbstractDocumentInputContext context
    ) throws ParseException
    {
        AdioTest1.ElementNode1 result = new AdioTest1.ElementNode1();
        result.childNonCdataValueNode1 = adid.deserializeStringValueInObject(node, true, "ChildNonCdataValueNode1");
        result.childNonCdataValueNode2 = adid.deserializeStringValueInObject(node, true, "ChildNonCdataValueNode2");
        result.childCdataValueNode1 = adid.deserializeStringValueInObject(node, true, "ChildCdataValueNode1");
        result.childCdataValueNode2 = adid.deserializeStringValueInObject(node, true, "ChildCdataValueNode2");
        return result;
    }
}
