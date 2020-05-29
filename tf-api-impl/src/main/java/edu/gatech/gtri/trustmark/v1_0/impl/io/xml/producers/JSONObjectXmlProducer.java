package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.HashMap;

/**
 * Created by brad on 1/7/16.
 */
public class JSONObjectXmlProducer extends AbstractXmlProducer implements XmlProducer {

    private static final Logger log = Logger.getLogger(JSONObjectXmlProducer.class);

    @Override
    public Class getSupportedType() {
        return JSONObject.class;
    }

    @Override
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        if( instance == null || !(instance instanceof JSONObject) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        JSONObject jsonObj = (JSONObject) instance;

//        HashMap attributes = new HashMap<>();
//        for( String key : jsonObj.keySet() ){
//            if( key.startsWith("$") ){
//                Object val = jsonObj.remove(key);
//                attributes.put(key, val);
//            }
//        }

        String xml = XML.toString(jsonObj);

        xml = xml.replaceAll("\\<\\$", "<");
        xml = xml.replaceAll("\\<\\/\\$", "</");

        log.debug("Generated JSONObject XML: "+xml);

        writeXmlToStream(xml, xmlWriter);
    }



}
