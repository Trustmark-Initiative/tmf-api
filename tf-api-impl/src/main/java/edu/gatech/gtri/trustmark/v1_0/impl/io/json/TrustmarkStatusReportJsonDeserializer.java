package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkStatusReportImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusCode;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.net.URI;

import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.assertSupported;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readDate;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readExtensionOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObject;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readJSONObjectList;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readString;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readStringOption;
import static edu.gatech.gtri.trustmark.v1_0.impl.io.json.JsonDeserializerUtility.readURI;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Created by brad on 12/10/15.
 */
public class TrustmarkStatusReportJsonDeserializer implements JsonDeserializer<TrustmarkStatusReport> {

    private static final Logger log = LogManager.getLogger(TrustmarkJsonDeserializer.class);

    public TrustmarkStatusReport deserialize(final String jsonString) throws ParseException {
        requireNonNull(jsonString);

        log.debug("Deserializing Trustmark Status Report JSON . . .");

        final JSONObject jsonObject = new JSONObject(jsonString);

        assertSupported(jsonObject);

        final TrustmarkStatusReportImpl trustmarkStatusReport = new TrustmarkStatusReportImpl();

        trustmarkStatusReport.setOriginalSource(jsonString);
        trustmarkStatusReport.setOriginalSourceType(SerializerJson.APPLICATION_JSON);

        trustmarkStatusReport.setId(readString(jsonObject, "$id"));
        trustmarkStatusReport.setStatus(readTrustmarkStatusCode(readString(jsonObject, "StatusCode")));
        trustmarkStatusReport.setStatusDateTime(readDate(jsonObject, "StatusDateTime"));
        trustmarkStatusReport.setTrustmarkReference(readURI(readJSONObject(jsonObject, "TrustmarkReference"), "Identifier"));

        readExtensionOption(jsonObject, "Extension").forEach(trustmarkStatusReport::setExtension);

        readJSONObjectList(jsonObject, "SupersederTrustmarkReferences").mapException(TrustmarkStatusReportJsonDeserializer::readSupersederTrustmarkReference).forEach(trustmarkStatusReport::addSupersederTrustmarkReference);

        readStringOption(jsonObject, "Notes").forEach(trustmarkStatusReport::setNotes);

        return trustmarkStatusReport;
    }

    private static TrustmarkStatusCode readTrustmarkStatusCode(final String string) throws ParseException {
        requireNonNull(string);

        final TrustmarkStatusCode trustmarkStatusCode = TrustmarkStatusCode.fromString(string);

        if (trustmarkStatusCode != null) {
            return trustmarkStatusCode;
        } else {
            throw new ParseException(format("The json object must have the 'TrustmarkStatusCode' property 'StatusCode'."));
        }
    }

    private static URI readSupersederTrustmarkReference(final JSONObject jsonObject) throws ParseException {
        requireNonNull(jsonObject);

        return readURI(jsonObject, "Identifier");
    }
}
