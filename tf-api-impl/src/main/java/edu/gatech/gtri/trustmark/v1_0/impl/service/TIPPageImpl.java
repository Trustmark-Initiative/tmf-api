package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.impl.io.IOUtils;
import edu.gatech.gtri.trustmark.v1_0.service.Page;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteTrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteTrustmarkDefinition;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by brad on 2/4/16.
 */
public class TIPPageImpl extends AbstractPageImpl implements Page<RemoteTrustInteroperabilityProfile> {

    private static final Logger log = Logger.getLogger(TIPPageImpl.class);

    public TIPPageImpl(JSONObject jsonObject) throws RemoteException {
        this.count = jsonObject.getLong("trustInteroperabilityProfilesCount");
        this.offset = jsonObject.getLong("offset");
        this.totalCount = jsonObject.getLong("totalCount");
        if(jsonObject.getJSONObject("_links").optJSONObject("next") != null ){
            String nextUrlString = jsonObject.getJSONObject("_links").getJSONObject("next").getString("href");
            try {
                this.nextPageUrl = new URL(nextUrlString);
            }catch(MalformedURLException me){
                throw new RemoteException("Cannot parse next URL, not valid: "+nextUrlString, me);
            }
        }

        if( jsonObject.optJSONArray("trustInteroperabilityProfiles") != null ) {
            JSONArray tipJsonArray = jsonObject.getJSONArray("trustInteroperabilityProfiles");
            for( int i = 0; i < tipJsonArray.length(); i++ ){
                JSONObject tipJson = tipJsonArray.getJSONObject(i);
//                log.info("Parsing TIP JSON: "+tipJson.toString(2));
                this.addObject(new RemoteTrustInteroperabilityProfileImpl(tipJson));
            }
        }
    }

    private URL nextPageUrl;
    private List<RemoteTrustInteroperabilityProfile> objects = new ArrayList<>();

    @Override
    public Class getClassType() {
        return RemoteTrustmarkDefinition.class;
    }


    @Override
    public List<RemoteTrustInteroperabilityProfile> getObjects() {
        if( objects == null )
            objects = new ArrayList<>();
        return objects;
    }

    public void addObject(RemoteTrustInteroperabilityProfile td){
        this.getObjects().add(td);
    }

    @Override
    public Boolean hasNext() {
        return nextPageUrl != null;
    }

    @Override
    public Page<RemoteTrustInteroperabilityProfile> next() throws RemoteException {
        if( nextPageUrl == null )
            throw new NoSuchElementException();
        return new TIPPageImpl(IOUtils.fetchJSON(nextPageUrl));
    }//end next()


}//end TDPageImpl