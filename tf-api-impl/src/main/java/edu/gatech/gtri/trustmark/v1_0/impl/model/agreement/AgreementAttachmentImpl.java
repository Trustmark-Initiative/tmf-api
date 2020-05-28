package edu.gatech.gtri.trustmark.v1_0.impl.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.impl.model.BinaryDataImpl;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AgreementAttachment;
import edu.gatech.gtri.trustmark.v1_0.model.agreement.AttachmentType;

/**
 * Created by Nicholas Saney on 2017-08-31.
 */
public class AgreementAttachmentImpl
    extends AbstractOrderedObjectAdapter<AgreementAttachment>
    implements AgreementAttachment
{
    
    ////// Instance Fields //////
    protected String name;
    protected String description;
    protected AttachmentType attachmentType;
    protected BinaryDataImpl data;
    
    
    ////// Instance Methods //////
    @Override
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    
    @Override
    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }
    
    @Override
    public AttachmentType getAttachmentType() { return this.attachmentType; }
    public void setAttachmentType(AttachmentType attachmentType) { this.attachmentType = attachmentType; }
    
    @Override
    public BinaryDataImpl getData() {
        if (this.data == null) { this.data = new BinaryDataImpl(); }
        return this.data;
    }
    
}
