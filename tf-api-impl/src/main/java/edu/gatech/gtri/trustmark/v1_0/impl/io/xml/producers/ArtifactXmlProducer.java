package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.model.Artifact;
import edu.gatech.gtri.trustmark.v1_0.model.Term;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;


/**
 * Created by brad on 1/7/16.
 */
public class ArtifactXmlProducer extends AbstractXmlProducer implements XmlProducer {

    private static final Logger log = Logger.getLogger(ArtifactXmlProducer.class);

    @Override
    public Class getSupportedType() {
        return Artifact.class;
    }

    @Override
    public void serialize(Object instance, XMLStreamWriter xmlWriter) throws XMLStreamException {
        if( instance == null || !(instance instanceof Artifact) )
            throw new IllegalArgumentException("Invalid argument passed to "+this.getClass().getSimpleName()+"!  Expecting non-null instance of class["+this.getSupportedType().getName()+"]!");

        Artifact artifact = (Artifact) instance;

        log.debug("Writing XML for Artifact["+artifact.getName()+"]...");

        xmlWriter.writeStartElement(NAMESPACE_URI, "Name");
        xmlWriter.writeCharacters(artifact.getName());
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement(NAMESPACE_URI, "Description");
        xmlWriter.writeCData(artifact.getDescription());
        xmlWriter.writeEndElement();

        log.debug("Successfully wrote Artifact XML!");
    }//end serialize()


}//end TrustmarkStatusReportXmlProducer