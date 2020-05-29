package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.*;
import edu.gatech.gtri.trustmark.v1_0.impl.model.agreement.TrustInteroperabilityProfileSnapshotImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.TrustInteroperabilityProfileSnapshot;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.dom4j.QName;

import java.util.Map;
import java.util.Set;

/**
 * Created by Nicholas on 02/02/2017.
 */
public class TrustInteroperabilityProfileSnapshotCodec extends Codec<TrustInteroperabilityProfileSnapshot> {
    @Override
    public Class<TrustInteroperabilityProfileSnapshot> getSupportedType() { return TrustInteroperabilityProfileSnapshot.class; }
    
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
    public String getIdFor(TrustInteroperabilityProfileSnapshot obj) { return null; }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(
        TrustInteroperabilityProfileSnapshot obj,
        AbstractDocumentOutputSerializer<TrustInteroperabilityProfileSnapshot, W, Ex> ados,
        AbstractDocumentOutputContext<W> context
    ) throws Ex
    {
        ados.serializeValueInObject(context, "SnapshotDateTime", obj.getSnapshotDateTime());
        ados.serializeValueInObject(context, "Index", obj.getIndex());
        ados.serializeValueInObject(context, "ExpressionId", obj.getExpressionId());
        ados.serializeNonCodecObjectInObject(context, "TrustInteroperabilityProfile", obj.getTrustInteroperabilityProfile());
    }
    
    @Override
    public <O> TrustInteroperabilityProfileSnapshot deserializeFullObject(
        O node,
        String id,
        String sourceString,
        String sourceType,
        AbstractDocumentInputDeserializer<TrustInteroperabilityProfileSnapshot, O, ?, ?> adid,
        AbstractDocumentInputContext context
    ) throws ParseException
    {
        TrustInteroperabilityProfileSnapshotImpl result = new TrustInteroperabilityProfileSnapshotImpl();
        result.setSnapshotDateTime(adid.deserializeDateValueInObject(node, true, "SnapshotDateTime"));
        result.setIndex(adid.deserializeNumberValueInObject(node, true, "Index").intValue());
        result.setExpressionId(adid.deserializeStringValueInObject(node, false, "ExpressionId"));
        result.setTrustInteroperabilityProfile(adid.deserializeTipInObject(node, true, "TrustInteroperabilityProfile"));
        return result;
    }
}
