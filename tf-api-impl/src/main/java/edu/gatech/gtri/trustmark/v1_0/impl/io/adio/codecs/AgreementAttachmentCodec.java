package edu.gatech.gtri.trustmark.v1_0.impl.io.adio.codecs;

import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentInputContext;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentInputDeserializer;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentOutputContext;
import edu.gatech.gtri.trustmark.v1_0.impl.io.adio.AbstractDocumentOutputSerializer;
import edu.gatech.gtri.trustmark.v1_0.impl.model.agreement.AgreementAttachmentImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementAttachment;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AttachmentType;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.SetValuedMap;
import org.dom4j.QName;

import java.util.*;

/**
 * Created by Nicholas Saney on 2017-09-01.
 */
public class AgreementAttachmentCodec extends Codec<AgreementAttachment>  {
    @Override
    public Class<AgreementAttachment> getSupportedType() { return AgreementAttachment.class; }
    
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
        return new LinkedHashSet<>(Arrays.asList(
            "Name",
            "Description"
        ));
    }
    
    @Override
    public String getIdFor(AgreementAttachment obj) { return null; }
    
    @Override
    public <W, Ex extends Exception> void serializeChildrenFor(
        AgreementAttachment obj,
        AbstractDocumentOutputSerializer<AgreementAttachment, W, Ex> ados,
        AbstractDocumentOutputContext<W> context
    )
        throws Ex
    {
        ados.serializeValueInObject(context, "Index", obj.getListIndex());
        ados.serializeValueInObject(context, "Name", obj.getName());
        ados.serializeValueInObject(context, "Description", obj.getDescription());
        ados.serializeValueInObject(context, "MimeType", obj.getAttachmentType().mimeType);
        ados.serializeValueInObject(context, "DataBase64", obj.getData().getBytesBase64());
    }
    
    @Override
    public <O> AgreementAttachment deserializeFullObject(
        O node,
        String id,
        String sourceString,
        String sourceType,
        AbstractDocumentInputDeserializer<AgreementAttachment, O, ?, ?> adid,
        AbstractDocumentInputContext context
    )
        throws ParseException
    {
        AgreementAttachmentImpl result = new AgreementAttachmentImpl();
        result.setListIndex(adid.deserializeNumberValueInObject(node, true, "Index").intValue());
        result.setName(adid.deserializeStringValueInObject(node, true, "Name"));
        result.setDescription(adid.deserializeStringValueInObject(node, true, "Description"));
        result.setAttachmentType(AttachmentType.fromMimeType(adid.deserializeStringValueInObject(node, true, "MimeType")));
        result.getData().setBytesBase64(adid.deserializeStringValueInObject(node, true, "DataBase64"));
        return result;
    }
}
