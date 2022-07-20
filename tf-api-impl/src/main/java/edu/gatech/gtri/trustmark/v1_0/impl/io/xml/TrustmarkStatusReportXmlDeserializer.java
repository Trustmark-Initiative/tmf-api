package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkStatusReportImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusCode;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getDate;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getString;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.getUri;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.xml.XmlDeserializerUtility.readExtension;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Implementations deserialize XML and, optionally, a URI into a Trustmark
 * Status Report.
 *
 * @author GTRI Trustmark Team
 */
public class TrustmarkStatusReportXmlDeserializer implements XmlDeserializer<TrustmarkStatusReport> {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkStatusReportXmlDeserializer.class);

    public TrustmarkStatusReport deserialize(final String xml, final URI uri) throws ParseException {

        requireNonNull(xml);

        log.debug("Deserializing Trustmark Status Report XML . . .");

        XmlHelper.validateXml(xml);

        final Element element = XmlHelper.readWithDom4j(xml);
        final String elementName = "TrustmarkStatusReport";

        if (!element.getQName().getName().equals(elementName)) {
            throw new ParseException(format("The document element of a Trustmark Status Report must be '%s'; it was '%s'.", elementName, element.getName()));

        }

        return fromDom4j(element, uri);
    }

    public static TrustmarkStatusReport fromDom4j(final Element element, final URI uri) throws ParseException {

        final TrustmarkStatusReportImpl trustmarkStatusReport = new TrustmarkStatusReportImpl();

        trustmarkStatusReport.setId(getString(element, "./@tf:id", true));

        trustmarkStatusReport.setTrustmarkReference(getUri(element, "./tf:TrustmarkReference/tf:Identifier", true));

        String statusCode = getString(element, "./tf:StatusCode", true);
        trustmarkStatusReport.setStatus(TrustmarkStatusCode.fromString(statusCode));

        trustmarkStatusReport.setStatusDateTime(getDate(element, "./tf:StatusDateTime", true));

        List<Node> supersederRefElements = element.selectNodes("./tf:SupersederTrustmarkReference");
        if (supersederRefElements != null && !supersederRefElements.isEmpty()) {
            for (Node supersederXml : supersederRefElements) {
                trustmarkStatusReport.addSupersederTrustmarkReference(getUri(supersederXml, "./tf:Identifier", true));
            }
        }
        trustmarkStatusReport.setNotes(getString(element, "./tf:Notes", false));
        trustmarkStatusReport.setExtension(readExtension((Element) element.selectSingleNode("./tf:Extension")));
        return trustmarkStatusReport;
    }//end fromDom4()


}//end TrustmarkXmlDeserializer
