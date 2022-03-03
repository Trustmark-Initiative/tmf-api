package edu.gatech.gtri.trustmark.v1_0.model.agreement;

import edu.gatech.gtri.trustmark.v1_0.io.ParseException;

import java.util.stream.Stream;

/**
 * The type of an AgreementAttachment.
 *
 * TODO: Merge with MediaType.
 *
 * @author Nicholas Saney
 */
public enum AttachmentType {
    
    PDF("application/pdf"),
    GIF("image/gif"),
    JPEG("image/jpeg"),
    PNG("image/png");
    
    public final String mimeType;
    
    AttachmentType(String _mimeType) {
        this.mimeType = _mimeType;
    }
    
    public static AttachmentType fromMimeType(String mimeType) throws ParseException {
        return Stream.of(AttachmentType.values())
                     .filter(at -> at.mimeType.equals(mimeType))
                     .findFirst()
                     .orElseThrow(() -> new ParseException("Invalid mime type for AttachmentType: " + mimeType))
        ;
    }
    
}
