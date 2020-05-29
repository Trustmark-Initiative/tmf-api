package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.*;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.dom4j.QName;

import java.util.*;

/**
 * Created by Nicholas on 01/25/2017.
 */
public class AdioTest1Codec extends Codec<AdioTest1> {
    
    @Override
    public Class<AdioTest1> getSupportedType() { return AdioTest1.class; }
    
    @Override
    public Map<String, QName> getNonTfQualifiedNamesByElementName() { return null; }
    
    @Override
    public SetValuedMap<String, String> getAttributeNamesByElementName() { return null; }
    
    @Override
    public BidiMap<String, Class<?>> getObjectSequenceParentNameChildTypeMap() {
        BidiMap<String, Class<?>> result = new DualHashBidiMap<>();
        result.put("SequenceNode1", AdioTest1.SequenceChild1.class);
        return result;
    }
    
    @Override
    public BidiMap<String, String> getValueSequenceParentNameChildNameMap() { return null; }
    
    @Override
    public Set<String> getCdataValueNames() {
        return new HashSet<>(Arrays.asList(
            "CdataValueNode1",
            "CdataValueNode2"
        ));
    }
    
    @Override
    public String getIdFor(AdioTest1 obj) { return null; }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(
        AdioTest1 obj,
        AbstractDocumentOutputSerializer<AdioTest1, W, Ex> ados,
        AbstractDocumentOutputContext<W> context
    )
        throws Ex
    {
        ados.serializeValueInObject(context, "NonCdataValueNode1", obj.nonCdataValueNode1);
        ados.serializeValueInObject(context, "NonCdataValueNode2", obj.nonCdataValueNode2);
        ados.serializeValueInObject(context, "CdataValueNode1", obj.cdataValueNode1);
        ados.serializeValueInObject(context, "CdataValueNode2", obj.cdataValueNode2);
        ados.serializeObjectInObject(context, AdioTest1.ElementNode1.class, obj.elementNode1);
        ados.serializeObjectInObject(context, AdioTest1.ElementNode2.class, obj.elementNode2);
        ados.serializeObjectSequenceInObject(context, AdioTest1.SequenceChild1.class, obj.sequenceChild1List);
    }
    
    @Override
    public <O> AdioTest1 deserializeFullObject(
        O node,
        String id,
        String sourceString,
        String sourceType,
        AbstractDocumentInputDeserializer<AdioTest1, O, ?, ?> adid,
        AbstractDocumentInputContext context
    )
        throws ParseException
    {
        AdioTest1 result = new AdioTest1();
        result.nonCdataValueNode1 = adid.deserializeStringValueInObject(node, true, "NonCdataValueNode1");
        result.nonCdataValueNode2 = adid.deserializeStringValueInObject(node, true, "NonCdataValueNode2");
        result.cdataValueNode1 = adid.deserializeStringValueInObject(node, true, "CdataValueNode1");
        result.cdataValueNode2 = adid.deserializeStringValueInObject(node, true, "CdataValueNode2");
        result.elementNode1 = adid.deserializeObjectInObject(node, context, true, AdioTest1.ElementNode1.class);
        result.elementNode2 = adid.deserializeObjectInObject(node, context, true, AdioTest1.ElementNode2.class);
        result.sequenceChild1List = adid.deserializeObjectSequenceInObject(node, context, true, AdioTest1.SequenceChild1.class);
        return result;
    }
    
}
