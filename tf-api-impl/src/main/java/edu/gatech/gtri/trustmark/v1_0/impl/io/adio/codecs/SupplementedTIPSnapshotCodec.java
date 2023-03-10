package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.*;
import edu.gatech.gtri.trustmark.v1_0.impl.model.agreement.SupplementedTIPSnapshotImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.SupplementedTIPSnapshot;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.TrustInteroperabilityProfileSnapshot;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.TrustmarkDefinitionSnapshot;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.dom4j.QName;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nicholas on 02/02/2017.
 */
public class SupplementedTIPSnapshotCodec extends Codec<SupplementedTIPSnapshot> {
    @Override
    public Class<SupplementedTIPSnapshot> getSupportedType() { return SupplementedTIPSnapshot.class; }
    
    @Override
    public Map<String, QName> getNonTfQualifiedNamesByElementName() { return null; }
    
    @Override
    public SetValuedMap<String, String> getAttributeNamesByElementName() { return null; }
    
    @Override
    public BidiMap<String, Class<?>> getObjectSequenceParentNameChildTypeMap() {
        BidiMap<String, Class<?>> result = new DualHashBidiMap<>();
        result.put("SupplementedTIPSnapshots", SupplementedTIPSnapshot.class);
        result.put("TrustmarkDefinitionSnapshots", TrustmarkDefinitionSnapshot.class);
        return result;
    }
    
    @Override
    public BidiMap<String, String> getValueSequenceParentNameChildNameMap() { return null; }
    
    @Override
    public Set<String> getCdataValueNames() {
        return new LinkedHashSet<>(Arrays.asList(
            "SupplementalLegalText"
        ));
    }
    
    @Override
    public String getIdFor(SupplementedTIPSnapshot obj) { return obj.getTrustInteroperabilityProfileSnapshot().getTrustInteroperabilityProfile().getIdentifier().toString(); }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(
        SupplementedTIPSnapshot obj,
        AbstractDocumentOutputSerializer<SupplementedTIPSnapshot, W, Ex> ados,
        AbstractDocumentOutputContext<W> context
    ) throws Ex
    {
        ados.serializeValueInObject(context, "SupplementalLegalText", obj.getSupplementalLegalText());
        ados.serializeObjectInObject(context, TrustInteroperabilityProfileSnapshot.class, obj.getTrustInteroperabilityProfileSnapshot());
        ados.serializeObjectSequenceInObject(context, TrustmarkDefinitionSnapshot.class, obj.getTrustmarkDefinitionSnapshots());
        ados.serializeObjectSequenceInObject(context, SupplementedTIPSnapshot.class, obj.getSupplementedTIPSnapshots());
    }
    
    @Override
    public <O> SupplementedTIPSnapshot deserializeFullObject(
        O node,
        String id,
        String sourceString,
        String sourceType,
        AbstractDocumentInputDeserializer<SupplementedTIPSnapshot, O, ?, ?> adid,
        AbstractDocumentInputContext context
    ) throws ParseException
    {
        SupplementedTIPSnapshotImpl result = new SupplementedTIPSnapshotImpl();
        result.setSupplementalLegalText(adid.deserializeStringValueInObject(node, false, "SupplementalLegalText"));
        result.setTrustInteroperabilityProfileSnapshot(adid.deserializeObjectInObject(node, context, true, TrustInteroperabilityProfileSnapshot.class));
        result.setTrustmarkDefinitionSnapshots(adid.deserializeObjectSequenceInObject(node, context, true, TrustmarkDefinitionSnapshot.class));
        result.setSupplementedTIPSnapshots(adid.deserializeObjectSequenceInObject(node, context, true, SupplementedTIPSnapshot.class));
        return result;
    }
}
