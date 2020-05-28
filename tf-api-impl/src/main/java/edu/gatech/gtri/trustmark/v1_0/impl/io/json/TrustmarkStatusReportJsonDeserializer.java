package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkStatusReportImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusCode;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by brad on 12/10/15.
 */
public class TrustmarkStatusReportJsonDeserializer extends AbstractDeserializer {


    private static final Logger log = Logger.getLogger(TrustmarkJsonDeserializer.class);


    public static TrustmarkStatusReport deserialize(String jsonString ) throws ParseException {
        log.debug("Deserializing TrustmarkStatusReport JSON...");

        JSONObject jsonObject = new JSONObject(jsonString);
        isSupported(jsonObject);

        TrustmarkStatusReportImpl tsr = new TrustmarkStatusReportImpl();
        tsr.setOriginalSource(jsonString);
        tsr.setOriginalSourceType("application/json");

        tsr.setId(getString(jsonObject, "$id", true));

        JSONObject tmRefObj = jsonObject.optJSONObject("TrustmarkReference");
        if( tmRefObj == null )
            throw new ParseException("TrustmarkStatusReports requires a JSON Object named 'TrustmarkReference' be defined.");
        tsr.setTrustmarkReference(getUri(tmRefObj, "Identifier", true));

        String statusCodeString = getString(jsonObject, "StatusCode", true);
        TrustmarkStatusCode statusCode = TrustmarkStatusCode.fromString(statusCodeString);
        if( statusCode == null )
            throw new ParseException("Status Code '"+statusCodeString+"' is invalid");
        tsr.setStatus(statusCode);

        tsr.setStatusDateTime(getDate(jsonObject, "StatusDateTime", true));

        JSONArray superseders = jsonObject.optJSONArray("SupersederTrustmarkReferences");
        if( superseders != null ){
            for( int i = 0; i < superseders.length(); i++ ){
                JSONObject supersederObj = (JSONObject) superseders.get(i);
                tsr.addSupersederTrustmarkReference(getUri(supersederObj, "Identifier", true));
            }
        }

        tsr.setNotes(getString(jsonObject, "Notes", false));
        tsr.setExtension(readExtension(jsonObject, "Extension", false));

        return tsr;
    }//end deserialize


}
