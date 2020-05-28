package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.impl.io.json.SerializerJson;
import edu.gatech.gtri.trustmark.v1_0.impl.io.xml.SerializerXml;
import edu.gatech.gtri.trustmark.v1_0.io.Serializer;
import edu.gatech.gtri.trustmark.v1_0.io.SerializerFactory;

/**
 * Created by brad on 12/15/15.
 */
public class SerializerFactoryImpl implements SerializerFactory {


    @Override
    public Serializer getSerializer(String contentType) {
        if (contentType.trim().equalsIgnoreCase("application/json") ){
            return getJsonSerializer();
        }else if( contentType.trim().equalsIgnoreCase("application/xml") ||
                contentType.trim().equalsIgnoreCase("text/xml") ){
            return getXmlSerializer();
        }else if(contentType.trim().equalsIgnoreCase("text/html")){
            return getHtmlSerializer();
        }else if(contentType.trim().equalsIgnoreCase("application/pdf")){
            return getPdfSerializer();
        }else{
            // Could not find anything for the given content type.
            return null;
        }
    }

    @Override
    public Serializer getXmlSerializer() {
        return new SerializerXml();
    }

    @Override
    public Serializer getJsonSerializer() {
        return new SerializerJson();
    }

    @Override
    public Serializer getHtmlSerializer() {
        return new SerializerHtml();
    }

    @Override
    public Serializer getHtmlEditorSerializer() {
        return new SerializerHtmlEditors();
    }
    
    @Override
    public Serializer getPdfSerializer() {
        return new SerializerPdf();
    }
}
