package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by brad on 2/5/16.
 */
public abstract class AbstractTFIDObject extends RemoteObjectImpl implements TrustmarkFrameworkIdentifiedObject {

    public static final String NAME_KEY = "Name";
    public static final String VERSION_KEY = "Version";
    public static final String DESC_KEY = "Description";
    public static final String ID_KEY = "Identifier";
    public static final String NUMBER_KEY = "Number";
    public static final String DEPRECATED_KEY = "Deprecated";
    public static final String PUBLICATION_DATETIME_KEY = "PublicationDateTime";
    public static final String KEYWORDS_KEY = "Keywords";

    protected AbstractTFIDObject(){}
    protected AbstractTFIDObject(String typeName, JSONObject json) throws RemoteException {
        this.setTypeName(typeName);
        try {
            this.setName(json.optString(NAME_KEY));
            this.setVersion(json.optString(VERSION_KEY));
            this.setDescription(json.optString(DESC_KEY));
            this.setIdentifier(new URI(json.optString(ID_KEY)));
            if(json.optString(NUMBER_KEY) != null && !json.optString(NUMBER_KEY).equals("")) {
                this.setNumber(Integer.parseInt(json.optString(NUMBER_KEY)));
            }

            if (json.optJSONObject("_links") != null)
                this.setFormats(this.buildFormats(json.optJSONObject("_links")));
        }catch(MalformedURLException me){
            throw new RemoteException("Encountered invalid URL while parsing format links.", me);
        }catch(NumberFormatException nfe){
            throw new RemoteException("Encountered invalid Integer while parsing format links.", nfe);
        }catch(URISyntaxException urise){
            throw new RemoteException("Invalid URI encountered for TMF identifier: "+json.optString("identifier"), urise);
        }catch(JSONException jsone){
            throw new RemoteException("Bad TMFObj JSON encountered", jsone);
        }
    }


    private String typeName;
    private URI identifier;
    private String name;
    private String version;
    private String description;
    private Integer number;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public URI getIdentifier() {
        return identifier;
    }

    public void setIdentifier(URI identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
