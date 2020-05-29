package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.*;
import edu.gatech.gtri.trustmark.v1_0.impl.model.agreement.AgreementNonBindingSectionImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementNonBindingSection;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.dom4j.QName;

import java.util.*;

/**
 * Created by Nicholas on 01/30/2017.
 */
public class AgreementNonBindingSectionCodec extends Codec<AgreementNonBindingSection> {
    @Override
    public Class<AgreementNonBindingSection> getSupportedType() { return AgreementNonBindingSection.class; }
    
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
        return new HashSet<>(Arrays.asList(
            "Title",
            "Text"
        ));
    }
    
    @Override
    public String getIdFor(AgreementNonBindingSection obj) { return null; }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(
        AgreementNonBindingSection obj,
        AbstractDocumentOutputSerializer<AgreementNonBindingSection, W, Ex> ados,
        AbstractDocumentOutputContext<W> context
    ) throws Ex
    {
        ados.serializeValueInObject(context, "Index", obj.getListIndex());
        ados.serializeValueInObject(context, "Title", obj.getTitle());
        ados.serializeValueInObject(context, "Text", obj.getText());
    }
    
    @Override
    public <O> AgreementNonBindingSection deserializeFullObject(
        O node,
        String id,
        String sourceString,
        String sourceType,
        AbstractDocumentInputDeserializer<AgreementNonBindingSection, O, ?, ?> adid,
        AbstractDocumentInputContext context
    ) throws ParseException
    {
        AgreementNonBindingSectionImpl result = new AgreementNonBindingSectionImpl();
        result.setListIndex(adid.deserializeNumberValueInObject(node, true, "Index").intValue());
        result.setTitle(adid.deserializeStringValueInObject(node, true, "Title"));
        result.setText(adid.deserializeStringValueInObject(node, true, "Text"));
        return result;
    }
}
