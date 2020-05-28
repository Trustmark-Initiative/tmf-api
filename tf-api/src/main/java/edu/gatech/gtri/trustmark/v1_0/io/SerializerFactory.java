package edu.gatech.gtri.trustmark.v1_0.io;

/**
 * Responsible for creating serializers.
 * <br/><br/>
 * Created by brad on 12/15/15.
 */
public interface SerializerFactory {

    /**
     * Returns the serializer most closely matching the given content type.  May return null.
     */
    public Serializer getSerializer(String contentType);

    /**
     * Returns a {@link Serializer} capable of writing XML which matches the Trustmark Framework v1.0 XML Spec.
     */
    public Serializer getXmlSerializer();

    /**
     * Returns a {@link Serializer} capable of writing unofficial JSON data (at least unofficial in v1.0 of TF spec)
     */
    public Serializer getJsonSerializer();

    /**
     * Returns a {@link Serializer} capable of writing HTML representing a visual version of TF artifacts.
     */
    public Serializer getHtmlSerializer();

    /**
     * Renders artifacts as HTML, but capable of editing.
     */
    public Serializer getHtmlEditorSerializer();
    
    /**
     * Returns a {@link Serializer} capable of writing to PDF.
     */
    public Serializer getPdfSerializer();

}//end SerializerFactory