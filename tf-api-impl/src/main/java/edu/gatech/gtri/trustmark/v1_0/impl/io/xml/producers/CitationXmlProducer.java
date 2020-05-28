package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Citation;
import edu.gatech.gtri.trustmark.v1_0.model.Contact;
import edu.gatech.gtri.trustmark.v1_0.model.Entity;
import edu.gatech.gtri.trustmark.v1_0.model.Source;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;

/**
 * Created by brad on 1/7/16.
 */
public class CitationXmlProducer extends AbstractXmlProducer implements XmlProducer {

    private static final Logger log = Logger.getLogger(CitationXmlProducer.class);

    @Override
    public Class getSupportedType() {
        return Citation.class;
    }

    @Override
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        if( instance == null || !(instance instanceof Citation) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Citation citation = (Citation) instance;

        log.debug("Writing XML for Citation["+citation.getSource().getIdentifier()+"]...");

        Source source = citation.getSource();
        xmlWriter.writeStartElement(NAMESPACE_URI, "Source");
        String sourceId = "Source"+source.getIdentifier().hashCode();
        xmlWriter.writeAttribute("tf", NAMESPACE_URI, "ref", sourceId);
        xmlWriter.writeAttribute("xsi", XSI_NS_URI, "nil", "true");
        xmlWriter.writeEndElement();


        if( citation.getDescription() != null ) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Description");
            xmlWriter.writeCData(citation.getDescription());
            xmlWriter.writeEndElement(); //end "Name"
        }

        log.debug("Finished writing XML citation!");
    }//end serialize()

}//end class EntityXmlProducer