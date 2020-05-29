package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.*;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkDefinitionRequirementImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.agreement.TrustmarkDefinitionSnapshotImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.TrustmarkDefinitionSnapshot;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.dom4j.QName;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nicholas on 02/02/2017.
 */
public class TrustmarkDefinitionSnapshotCodec extends Codec<TrustmarkDefinitionSnapshot> {
    @Override
    public Class<TrustmarkDefinitionSnapshot> getSupportedType() { return TrustmarkDefinitionSnapshot.class; }
    
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
    public String getIdFor(TrustmarkDefinitionSnapshot obj) { return null; }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(
        TrustmarkDefinitionSnapshot obj,
        AbstractDocumentOutputSerializer<TrustmarkDefinitionSnapshot, W, Ex> ados,
        AbstractDocumentOutputContext<W> context
    ) throws Ex
    {
        ados.serializeValueInObject(context, "SnapshotDateTime", obj.getSnapshotDateTime());
        ados.serializeValueInObject(context, "Index", obj.getIndex());
        ados.serializeValueInObject(context, "ExpressionId", obj.getExpressionId());
        Map<String,Object> tdReferenceInfo = new HashMap<>();
        tdReferenceInfo.put("Identifier", obj.getReference().getIdentifier());
        tdReferenceInfo.put("Name", obj.getReference().getName());
        tdReferenceInfo.put("Version", obj.getReference().getVersion());
        ados.serializeMapLiteralObjectInObject(context, "TrustmarkDefinitionReference", tdReferenceInfo);
    }
    
    @Override
    public <O> TrustmarkDefinitionSnapshot deserializeFullObject(
        O node,
        String id,
        String sourceString,
        String sourceType,
        AbstractDocumentInputDeserializer<TrustmarkDefinitionSnapshot, O, ?, ?> adid,
        AbstractDocumentInputContext context
    ) throws ParseException
    {
        TrustmarkDefinitionSnapshotImpl result = new TrustmarkDefinitionSnapshotImpl();
        result.setSnapshotDateTime(adid.deserializeDateValueInObject(node, true, "SnapshotDateTime"));
        result.setIndex(adid.deserializeNumberValueInObject(node, true, "Index").intValue());
        result.setExpressionId(adid.deserializeStringValueInObject(node, false, "ExpressionId"));
        TrustmarkDefinitionRequirementImpl tdReference = new TrustmarkDefinitionRequirementImpl();
        tdReference.setIdentifier(adid.deserializeUriValueInObject(node, true, "TrustmarkDefinitionReference/Identifier"));
        tdReference.setName(adid.deserializeStringValueInObject(node, true, "TrustmarkDefinitionReference/Name"));
        tdReference.setVersion(adid.deserializeStringValueInObject(node, true, "TrustmarkDefinitionReference/Version"));
        result.setReference(tdReference);
        return result;
    }
}
