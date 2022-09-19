package edu.gatech.gtri.trustmark.v1_0.impl.io.json;

import edu.gatech.gtri.trustmark.v1_0.impl.model.TrustmarkStatusReportImpl;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusCode;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Implementations deserialize JSON and, optionally, a URI into a Trust
 * Interoperability Profile.
 *
 * @author GTRI Trustmark Team
 */
public final class TrustmarkStatusReportJsonDeserializer implements JsonDeserializer<TrustmarkStatusReport> {

    private static final Logger log = LoggerFactory.getLogger(TrustmarkJsonDeserializer.class);

    public TrustmarkStatusReport deserialize(final String jsonString, final URI uri) throws ParseException {
        requireNonNull(jsonString);

        log.debug("Deserializing Trustmark Status Report JSON . . .");

        final JSONObject jsonObject = readJSONObject(jsonString);

        assertSupported(jsonObject);

        final TrustmarkStatusReportImpl trustmarkStatusReport = new TrustmarkStatusReportImpl();

        trustmarkStatusReport.setIdentifier(uri);

        readStringOption(jsonObject, "$id").forEach(trustmarkStatusReport::setId);
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
