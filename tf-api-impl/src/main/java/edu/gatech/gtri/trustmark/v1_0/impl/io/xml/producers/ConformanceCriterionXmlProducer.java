package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Citation;
import edu.gatech.gtri.trustmark.v1_0.model.ConformanceCriterion;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;

/**
 * Created by brad on 1/7/16.
 */
public class ConformanceCriterionXmlProducer implements XmlProducer<ConformanceCriterion> {

    private static final Logger log = Logger.getLogger(ConformanceCriterionXmlProducer.class);


    @Override
    public Class<ConformanceCriterion> getSupportedType() {
        return ConformanceCriterion.class;
    }

    @Override
    public void serialize(ConformanceCriterion crit, XMLStreamWriter xmlWriter) throws XMLStreamException {
        log.debug("Writing XML for ConformanceCriterion #" + crit.getNumber() + ": " + crit.getName());

        String id = "Criterion" + crit.getNumber();
        xmlWriter.writeAttribute("tf", NAMESPACE_URI, "id", id);

        xmlWriter.writeStartElement(NAMESPACE_URI, "Number");
        xmlWriter.writeCharacters(crit.getNumber().toString());
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "Name");
        xmlWriter.writeCharacters(crit.getName());
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "Description");
        xmlWriter.writeCharacters(crit.getDescription());
        xmlWriter.writeEndElement();

        if (crit.getCitations() != null && crit.getCitations().size() > 0) {
            for (Citation citation : crit.getCitations()) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "Citation");
                XmlProducerUtility.writeXml(citation, xmlWriter);
                xmlWriter.writeEndElement();
            }
        }

        log.debug("Successfully wrote XML for Criterion #" + crit.getNumber());
    }//end serialize()

}//end class EntityXmlProducer
