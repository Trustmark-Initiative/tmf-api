package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.*;
import edu.gatech.gtri.trustmark.v1_0.impl.model.agreement.AgreementLegalSectionImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementLegalSection;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.ListStyleType;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.dom4j.QName;

import java.util.*;

/**
 * Created by Nicholas on 01/30/2017.
 */
public class AgreementLegalSectionCodec extends Codec<AgreementLegalSection> {
    @Override
    public Class<AgreementLegalSection> getSupportedType() { return AgreementLegalSection.class; }
    
    @Override
    public Map<String, QName> getNonTfQualifiedNamesByElementName() { return null; }
    
    @Override
    public SetValuedMap<String, String> getAttributeNamesByElementName() { return null; }
    
    @Override
    public BidiMap<String, Class<?>> getObjectSequenceParentNameChildTypeMap() {
        BidiMap<String, Class<?>> result = new DualHashBidiMap<>();
        result.put("SubSections", AgreementLegalSection.class);
        return result;
    }
    
    @Override
    public BidiMap<String, String> getValueSequenceParentNameChildNameMap() { return null; }
    
    @Override
    public Set<String> getCdataValueNames() {
        return new HashSet<>(Arrays.asList(
            "Title",
            "Text"
        ));
    }
    
    @Override
    public String getIdFor(AgreementLegalSection obj) { return null; }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(
        AgreementLegalSection obj,
        AbstractDocumentOutputSerializer<AgreementLegalSection, W, Ex> ados,
        AbstractDocumentOutputContext<W> context
    ) throws Ex
    {
        ados.serializeValueInObject(context, "Index", obj.getListIndex());
        ados.serializeValueInObject(context, "Title", obj.getTitle());
        ados.serializeValueInObject(context, "Text", obj.getText());
        ados.serializeValueInObject(context, "ListStyleType", obj.getListStyleType());
        ados.serializeObjectSequenceInObject(context, AgreementLegalSection.class, obj.getLegalSections());
    }
    
    @Override
    public <O> AgreementLegalSection deserializeFullObject(
        O node,
        String id,
        String sourceString,
        String sourceType,
        AbstractDocumentInputDeserializer<AgreementLegalSection, O, ?, ?> adid,
        AbstractDocumentInputContext context
    ) throws ParseException
    {
        AgreementLegalSectionImpl result = new AgreementLegalSectionImpl();
        result.setListIndex(adid.deserializeNumberValueInObject(node, true, "Index").intValue());
        result.setTitle(adid.deserializeStringValueInObject(node, false, "Title"));
        result.setText(adid.deserializeStringValueInObject(node, false, "Text"));
        result.setListStyleType(adid.deserializeEnumValueInObject(node, false, ListStyleType.class, "ListStyleType"));
        result.setLegalSections(adid.deserializeObjectSequenceInObject(node, context, true, AgreementLegalSection.class));
        return result;
    }
}
