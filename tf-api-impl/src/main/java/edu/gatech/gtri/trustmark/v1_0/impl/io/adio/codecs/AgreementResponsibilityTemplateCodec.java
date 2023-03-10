package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentInputContext;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentInputDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentOutputContext;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentOutputSerializer;
import edu.gatech.gtri.trustmark.v1_0.impl.model.agreement.AgreementResponsibilityTemplateImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementResponsibilityTemplate;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.dom4j.QName;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nicholas Saney on 2017-06-06.
 */
public class AgreementResponsibilityTemplateCodec extends Codec<AgreementResponsibilityTemplate> {
    @Override
    public Class<AgreementResponsibilityTemplate> getSupportedType() { return AgreementResponsibilityTemplate.class; }
    
    @Override
    public Map<String, QName> getNonTfQualifiedNamesByElementName() { return null; }
    
    @Override
    public SetValuedMap<String, String> getAttributeNamesByElementName() { return null; }
    
    @Override
    public BidiMap<String, Class<?>> getObjectSequenceParentNameChildTypeMap() { return null; }
    
    @Override
    public BidiMap<String, String> getValueSequenceParentNameChildNameMap() {
        BidiMap<String, String> result = new DualHashBidiMap<>();
        result.put("TipIdentifiers", "TipIdentifier");
        return result;
    }
    
    @Override
    public Set<String> getCdataValueNames() {
        return new LinkedHashSet<>(Arrays.asList(
            "Definition"
        ));
    }
    
    @Override
    public String getIdFor(AgreementResponsibilityTemplate obj) { return null; }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(AgreementResponsibilityTemplate obj, AbstractDocumentOutputSerializer<AgreementResponsibilityTemplate, W, Ex> ados, AbstractDocumentOutputContext<W> context)
        throws Ex
    {
        ados.serializeValueInObject(context, "Name", obj.getName());
        ados.serializeValueInObject(context, "Category", obj.getCategory());
        ados.serializeValueInObject(context, "Definition", obj.getDefinition());
        ados.serializeValueSequenceInObject(context, "TipIdentifiers", obj.getTipIdentifiers());
    }
    
    @Override
    public <O> AgreementResponsibilityTemplate deserializeFullObject(O node, String id, String sourceString, String sourceType, AbstractDocumentInputDeserializer<AgreementResponsibilityTemplate, O, ?, ?> adid, AbstractDocumentInputContext context)
        throws ParseException
    {
        AgreementResponsibilityTemplateImpl result = new AgreementResponsibilityTemplateImpl();
        result.setName(adid.deserializeStringValueInObject(node, true, "Name"));
        result.setCategory(adid.deserializeStringValueInObject(node, true, "Category"));
        result.setDefinition(adid.deserializeStringValueInObject(node, true, "Definition"));
        result.setTipIdentifiers(adid.deserializeValueSequenceInObject(node, true, "TipIdentifiers", URI::new));
        return result;
    }
}
