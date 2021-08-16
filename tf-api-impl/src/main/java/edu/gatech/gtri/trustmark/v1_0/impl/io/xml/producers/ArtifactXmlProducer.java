package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Artifact;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;


/**
 * Created by brad on 1/7/16.
 */
public class ArtifactXmlProducer implements XmlProducer<Artifact> {

    private static final Logger log = LogManager.getLogger(ArtifactXmlProducer.class);

    @Override
    public Class<Artifact> getSupportedType() {
        return Artifact.class;
    }

    @Override
    public void serialize(Artifact artifact, XMLStreamWriter xmlWriter) throws XMLStreamException {
        log.debug("Writing XML for Artifact[" + artifact.getName() + "]...");

        xmlWriter.writeStartElement(NAMESPACE_URI, "Name");
        xmlWriter.writeCharacters(artifact.getName());
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "Description");
        xmlWriter.writeCData(artifact.getDescription());
        xmlWriter.writeEndElement();

        log.debug("Successfully wrote Artifact XML!");
    }//end serialize()


}//end TrustmarkStatusReportXmlProducer
