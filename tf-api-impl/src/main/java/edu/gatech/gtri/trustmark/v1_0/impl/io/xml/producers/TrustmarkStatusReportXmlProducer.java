package edu.gatech.gtri.trustmark.v1_0.impl.io.xml.producers;

import edu.gatech.gtri.trustmark.v1_0.impl.io.IdUtility;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlProducer;
import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlUtils;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.net.URI;
import java.util.UUID;

import static edu.gatech.gtri.trustmark.v1_0.impl.TrustmarkFrameworkConstants.NAMESPACE_URI;

/**
 * Created by brad on 1/7/16.
 */
public class TrustmarkStatusReportXmlProducer implements XmlProducer<TrustmarkStatusReport> {

    @Override
    public Class getSupportedType() {
        return TrustmarkStatusReport.class;
    }

    @Override
    public void serialize(TrustmarkStatusReport tsr, XMLStreamWriter xmlWriter) throws XMLStreamException {

        xmlWriter.writeAttribute(NAMESPACE_URI, "id", tsr.getId() == null ?
                IdUtility.trustmarkStatusReportId() :
                tsr.getId());

        // TODO Digital Signature?

        xmlWriter.writeStartElement(NAMESPACE_URI, "TrustmarkReference");
        xmlWriter.writeStartElement(NAMESPACE_URI, "Identifier");
        xmlWriter.writeCharacters(tsr.getTrustmarkReference().toString());
        xmlWriter.writeEndElement(); // end "Identifier"
        xmlWriter.writeEndElement(); // end "TrustmarkReference"

        xmlWriter.writeStartElement(NAMESPACE_URI, "StatusCode");
        xmlWriter.writeCharacters(tsr.getStatus().toString());
        xmlWriter.writeEndElement(); // end "StatusCode"

        xmlWriter.writeStartElement(NAMESPACE_URI, "StatusDateTime");
        xmlWriter.writeCharacters(XmlUtils.toDateTimeString(tsr.getStatusDateTime()));
        xmlWriter.writeEndElement(); // end "StatusCode"

        if (tsr.getSupersederTrustmarkReferences() != null && !tsr.getSupersederTrustmarkReferences().isEmpty()) {
            for (URI ref : tsr.getSupersederTrustmarkReferences()) {
                xmlWriter.writeStartElement(NAMESPACE_URI, "SupersederTrustmarkReference");
                xmlWriter.writeStartElement(NAMESPACE_URI, "Identifier");
                xmlWriter.writeCharacters(ref.toString());
                xmlWriter.writeEndElement(); // end "Identifier"
                xmlWriter.writeEndElement(); // end "SupersederTrustmarkReference"
            }
        }

        if (tsr.getNotes() != null) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Notes");
            xmlWriter.writeCharacters(tsr.getNotes());
            xmlWriter.writeEndElement(); // end "Notes"
        }

        if (tsr.getExtension() != null) {
            xmlWriter.writeStartElement(NAMESPACE_URI, "Extension");
            XmlProducerUtility.writeXml(tsr.getExtension(), xmlWriter);
//            xmlWriter.writeEndElement(); // end "Extension"
        }

    }


}//end TrustmarkStatusReportXmlProducer
