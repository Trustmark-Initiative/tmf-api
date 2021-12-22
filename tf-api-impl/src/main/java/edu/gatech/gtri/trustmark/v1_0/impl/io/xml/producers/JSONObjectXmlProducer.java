package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import org.json.XML;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Created by brad on 1/7/16.
 */
public class JSONObjectXmlProducer implements XmlProducer<JSONObject> {

    private static final Logger log = LoggerFactory.getLogger(JSONObjectXmlProducer.class);

    @Override
    public Class<JSONObject> getSupportedType() {
        return JSONObject.class;
    }

    @Override
    public void serialize(JSONObject jsonObj, XMLStreamWriter xmlWriter) throws XMLStreamException {
        String xml = XML.toString(jsonObj);

        xml = xml.replaceAll("\\<\\$", "<");
        xml = xml.replaceAll("\\<\\/\\$", "</");

        log.debug("Generated JSONObject XML: " + xml);

        XmlProducerUtility.writeXmlToStream(xml, xmlWriter);
    }


}
