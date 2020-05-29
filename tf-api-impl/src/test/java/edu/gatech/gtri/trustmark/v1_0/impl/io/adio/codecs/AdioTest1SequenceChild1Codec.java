package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.*;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.dom4j.QName;

import java.util.Map;
import java.util.Set;

/**
 * Created by Nicholas on 01/25/2017.
 */
public class AdioTest1SequenceChild1Codec extends Codec<AdioTest1.SequenceChild1> {
    @Override
    public Class<AdioTest1.SequenceChild1> getSupportedType() { return AdioTest1.SequenceChild1.class; }
    
    @Override
    public Map<String, QName> getNonTfQualifiedNamesByElementName() { return null; }
    
    @Override
    public SetValuedMap<String, String> getAttributeNamesByElementName() { return null; }
    
    @Override
    public BidiMap<String, Class<?>> getObjectSequenceParentNameChildTypeMap() { return null; }
    
    @Override
    public BidiMap<String, String> getValueSequenceParentNameChildNameMap() { return null; }
    
    @Override
    public Set<String> getCdataValueNames() { return null; }
    
    @Override
    public String getIdFor(AdioTest1.SequenceChild1 obj) { return null; }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(
        AdioTest1.SequenceChild1 obj,
        AbstractDocumentOutputSerializer<AdioTest1.SequenceChild1, W, Ex> ados,
        AbstractDocumentOutputContext<W> context
    ) throws Ex
    {
        ados.serializeValueInObject(context, "ChildNonCdataValueNode5", obj.childNonCdataValueNode5);
        ados.serializeObjectInObject(context, AdioTest1.ElementNode2.class, obj.elementNode2);
    }
    
    @Override
    public <O> AdioTest1.SequenceChild1 deserializeFullObject(
        O node,
        String id,
        String sourceString,
        String sourceType,
        AbstractDocumentInputDeserializer<AdioTest1.SequenceChild1, O, ?, ?> adid,
        AbstractDocumentInputContext context
    ) throws ParseException
    {
        AdioTest1.SequenceChild1 result = new AdioTest1.SequenceChild1();
        result.childNonCdataValueNode5 = adid.deserializeStringValueInObject(node, true, "ChildNonCdataValueNode5");
        result.elementNode2 = adid.deserializeObjectInObject(node, context, true, AdioTest1.ElementNode2.class);
        return result;
    }
}
