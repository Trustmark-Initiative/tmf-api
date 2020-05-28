package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentInputContext;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentInputDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentOutputContext;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentOutputSerializer;
import edu.gatech.gtri.trustmark.v1_0.impl.model.agreement.AgreementSignatoryImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementSignatory;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.dom4j.QName;

import java.util.Map;
import java.util.Set;

/**
 * Created by Nicholas Saney on 4/5/17.
 */
public class AgreementSignatoryCodec extends Codec<AgreementSignatory> {
    @Override
    public Class<AgreementSignatory> getSupportedType() { return AgreementSignatory.class; }
    
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
    public String getIdFor(AgreementSignatory obj) { return null; }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(AgreementSignatory obj, AbstractDocumentOutputSerializer<AgreementSignatory, W, Ex> ados, AbstractDocumentOutputContext<W> context)
        throws Ex
    {
        ados.serializeValueInObject(context, "IndividualName", obj.getIndividualName());
        ados.serializeValueInObject(context, "IndividualTitle", obj.getIndividualTitle());
        ados.serializeValueInObject(context, "DivisionName", obj.getDivisionName());
        ados.serializeValueInObject(context, "AuxiliaryText", obj.getAuxiliaryText());
    }
    
    @Override
    public <O> AgreementSignatory deserializeFullObject(O node, String id, String sourceString, String sourceType, AbstractDocumentInputDeserializer<AgreementSignatory, O, ?, ?> adid, AbstractDocumentInputContext context)
        throws ParseException
    {
        AgreementSignatoryImpl result = new AgreementSignatoryImpl();
        result.setIndividualName(adid.deserializeStringValueInObject(node, true, "IndividualName"));
        result.setIndividualTitle(adid.deserializeStringValueInObject(node, true, "IndividualTitle"));
        result.setDivisionName(adid.deserializeStringValueInObject(node, false, "DivisionName"));
        result.setAuxiliaryText(adid.deserializeStringValueInObject(node, false, "AuxiliaryText"));
        return result;
    }
}
