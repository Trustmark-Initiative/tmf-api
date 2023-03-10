package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.*;
import edu.gatech.gtri.trustmark.v1_0.impl.model.agreement.AgreementResponsibilityImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibility;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.SupplementedTIPSnapshot;
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
public class AgreementResponsibilityCodec extends Codec<AgreementResponsibility> {
    @Override
    public Class<AgreementResponsibility> getSupportedType() { return AgreementResponsibility.class; }
    
    @Override
    public Map<String, QName> getNonTfQualifiedNamesByElementName() { return null; }
    
    @Override
    public SetValuedMap<String, String> getAttributeNamesByElementName() { return null; }
    
    @Override
    public BidiMap<String, Class<?>> getObjectSequenceParentNameChildTypeMap() {
        BidiMap<String, Class<?>> result = new DualHashBidiMap<>();
        result.put("SupplementedTIPSnapshots", SupplementedTIPSnapshot.class);
        return result;
    }
    
    @Override
    public BidiMap<String, String> getValueSequenceParentNameChildNameMap() { return null; }
    
    @Override
    public Set<String> getCdataValueNames() {
        return new LinkedHashSet<>(Arrays.asList(
            "Definition"
        ));
    }
    
    @Override
    public String getIdFor(AgreementResponsibility obj) { return obj.getIdentifier(); }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(
        AgreementResponsibility obj,
        AbstractDocumentOutputSerializer<AgreementResponsibility, W, Ex> ados,
        AbstractDocumentOutputContext<W> context
    ) throws Ex
    {
        ados.serializeValueInObject(context, "Name", obj.getName());
        ados.serializeValueInObject(context, "Category", obj.getCategory());
        ados.serializeValueInObject(context, "Definition", obj.getDefinition());
        ados.serializeObjectSequenceInObject(context, SupplementedTIPSnapshot.class, obj.getSupplementedTIPSnapshots());
    }
    
    @Override
    public <O> AgreementResponsibility deserializeFullObject(
        O node,
        String id,
        String sourceString,
        String sourceType,
        AbstractDocumentInputDeserializer<AgreementResponsibility, O, ?, ?> adid,
        AbstractDocumentInputContext context
    ) throws ParseException
    {
        AgreementResponsibilityImpl result = new AgreementResponsibilityImpl();
        result.setIdentifier(id);
        result.setName(adid.deserializeStringValueInObject(node, true, "Name"));
        result.setCategory(adid.deserializeStringValueInObject(node, true, "Category"));
        result.setDefinition(adid.deserializeStringValueInObject(node, true, "Definition"));
        result.setSupplementedTIPSnapshots(adid.deserializeObjectSequenceInObject(node, context, true, SupplementedTIPSnapshot.class));
        return result;
    }
}
