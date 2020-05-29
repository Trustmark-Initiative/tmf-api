package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import org.apache.log4j.Logger;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.xml.stream.*;
import java.io.IOException;
import java.io.StringWriter;

/**
 * This {@link XmlProducer} is responsible for copying over generic XML Nodes.  Note that their elements will be copied
 * over as well, differing slightly from the rest of the serialization pattern.
 * <br/><br/>
 * Created by brad on 1/7/16.
 */
public class NodeXmlProducer extends AbstractXmlProducer implements XmlProducer {

    private static final Logger log = Logger.getLogger(NodeXmlProducer.class);

    @Override
    public Class getSupportedType() {
        return Node.class;
    }

    @Override
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        if( instance == null || !(instance instanceof Node) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Node node = (Node) instance;

        StringWriter inMemoryStringWriter = new StringWriter();
        XMLWriter writer = new XMLWriter(inMemoryStringWriter, OutputFormat.createPrettyPrint());
        try {
            writer.write(node);
            writer.flush();
            writer.close();
        }catch(IOException ioe){
            throw new UnsupportedOperationException("Not able to write node to in-memory string!", ioe);
        }

        String inMemoryString = inMemoryStringWriter.toString().trim();
        inMemoryStringWriter = null;


        log.debug("Produced this XML string: \n"+inMemoryString);

        writeXmlToStream(inMemoryString, xmlWriter);

    }//end serialize




}//end NodeXmlProducer