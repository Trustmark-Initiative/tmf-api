package edu.gatech.gtri.trustmark.v1_0.impl.io.xml;

import edu.gatech.gtri.trustmark.v1_0.impl.model.ExtensionImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkImpl;
import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkStatusReportImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusCode;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.List;

/**
 * Deserializes XML strings into Trustmark objects.
 * <br/><br/>
 * Created by brad on 12/9/15.
 */
public class TrustmarkStatusReportXmlDeserializer extends AbstractDeserializer {

    private static final Logger log = LogManager.getLogger(TrustmarkStatusReportXmlDeserializer.class);

    public static TrustmarkStatusReport deserialize(String xml ) throws ParseException {
        log.debug("Request to deserialize TrustmarkStatusReport XML...");

        XmlHelper.validateXml(xml);

        Element root = XmlHelper.readWithDom4j(xml);
        if( !root.getQName().getName().equals("TrustmarkStatusReport") ){
            throw new ParseException("Root element of a TrustmarkStatusReport must be tf:TrustmarkStatusReport, you have: "+root.getName());
        }

        TrustmarkStatusReport tsr = fromDom4j(root, xml);
        return tsr;
    }//end deserialize()


    public static TrustmarkStatusReport fromDom4j( Element tsrXml, String originalSource ) throws ParseException {
        log.debug("Constructing new TrustmarkStatusReport from element["+tsrXml.getName()+"]...");
        TrustmarkStatusReportImpl tsr = new TrustmarkStatusReportImpl();
        tsr.setOriginalSource(originalSource);
        tsr.setOriginalSourceType("text/xml");

        tsr.setId(getString(tsrXml, "./@tf:id", true));

        tsr.setTrustmarkReference(getUri(tsrXml, "./tf:TrustmarkReference/tf:Identifier", true));

        String statusCode = getString(tsrXml, "./tf:StatusCode", true);
        tsr.setStatus(TrustmarkStatusCode.fromString(statusCode));

        tsr.setStatusDateTime(getDate(tsrXml, "./tf:StatusDateTime", true));

        List<Node> supersederRefElements = tsrXml.selectNodes("./tf:SupersederTrustmarkReference");
        if( supersederRefElements != null && !supersederRefElements.isEmpty() ){
            for( Node supersederXml : supersederRefElements ){
                tsr.addSupersederTrustmarkReference(getUri(supersederXml, "./tf:Identifier", true));
            }
        }
        tsr.setNotes(getString(tsrXml, "./tf:Notes", false));
        tsr.setExtension(readExtension((Element) tsrXml.selectSingleNode("./tf:Extension")));
        return tsr;
    }//end fromDom4()


}//end TrustmarkXmlDeserializer