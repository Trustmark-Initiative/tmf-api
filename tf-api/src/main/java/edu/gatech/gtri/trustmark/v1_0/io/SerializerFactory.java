package edu.gatech.gtri.trustmark.v1_0.io;

/**
 * Implementations return serializers.
 *
 * @author GTRI Trustmark Team
 */
public interface SerializerFactory {

    /**
     * Returns a serializer for a content type.
     *
     * @param contentType
     * @return a serializer for a content type
     */
    Serializer getSerializer(final String contentType);

    /**
     * Returns a serializer for XML.
     *
     * @return a serializer for XML
     */
    Serializer getXmlSerializer();

    /**
     * Returns a serializer for JSON.
     *
     * @return a serializer for JSON
     */
    Serializer getJsonSerializer();

    /**
     * Returns a serializer for HTML.
     *
     * @return a serializer for HTML
     */
    Serializer getHtmlSerializer();

    /**
     * Returns a serialzier for editable HTML.
     *
     * @return a serializer for editable HTML
     */
    Serializer getHtmlEditorSerializer();

    /**
     * Returns a serializer for PDF.
     *
     * @return a serializer for PDF
     */
    Serializer getPdfSerializer();
}
