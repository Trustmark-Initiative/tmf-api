package edu.gatech.gtri.trustmark.v1_0.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.model.BinaryData;

/**
 * Some data (typically a document or image) that is attached to an Agreement.
 *
 * @author Nicholas Saney
 */
public interface AgreementAttachment extends AbstractOrderedObject<AgreementAttachment> {
    
    /**
     * The name of this attachment.
     */
    public String getName();
    
    /**
     * The description of this attachment.
     */
    public String getDescription();
    
    /**
     * The type of data in this attachment.
     */
    public AttachmentType getAttachmentType();
    
    /**
     * The data for this attachment.
     */
    public BinaryData getData();
    
}
