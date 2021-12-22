package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * This {@link XmlProducer} is responsible for copying over generic XML Nodes.  Note that their elements will be copied
 * over as well, differing slightly from the rest of the serialization pattern.
 * <br/><br/>
 * Created by brad on 1/7/16.
 */
public class NodeXmlProducer implements XmlProducer<Node> {

    private static final Logger log = LoggerFactory.getLogger(NodeXmlProducer.class);

    @Override
    public Class<Node> getSupportedType() {
        return Node.class;
    }

    @Override
    public void serialize(Node node, XMLStreamWriter xmlWriter) throws XMLStreamException {
        StringWriter inMemoryStringWriter = new StringWriter();
        XMLWriter writer = new XMLWriter(inMemoryStringWriter, OutputFormat.createPrettyPrint());
        try {
            writer.write(node);
            writer.flush();
            writer.close();
        } catch (IOException ioe) {
            throw new UnsupportedOperationException("Not able to write node to in-memory string!", ioe);
        }

        String inMemoryString = inMemoryStringWriter.toString().trim();
        inMemoryStringWriter = null;


        log.debug("Produced this XML string: \n" + inMemoryString);

        XmlProducerUtility.writeXmlToStream(inMemoryString, xmlWriter);

    }//end serialize


}//end NodeXmlProducer
