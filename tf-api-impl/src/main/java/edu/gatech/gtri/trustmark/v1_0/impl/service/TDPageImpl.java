package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.impl.io.IOUtils;
import edu.gatech.gtri.trustmark.v1_0.service.Page;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteException;
import edu.gatech.gtri.trustmark.v1_0.service.RemoteTrustmarkDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TDPageImpl extends AbstractPageImpl implements Page<RemoteTrustmarkDefinition> {

    private static final Logger log = LoggerFactory.getLogger(TDPageImpl.class);

    public TDPageImpl(JSONObject jsonObject) throws RemoteException {
        this.count = jsonObject.getLong("trustmarkDefinitionsCount");
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

        if( jsonObject.optJSONArray("trustmarkDefinitions") != null ) {
            JSONArray tdArray = jsonObject.getJSONArray("trustmarkDefinitions");
            for( int i = 0; i < tdArray.length(); i++ ){
                JSONObject tdJson = tdArray.getJSONObject(i);
                this.addObject(new RemoteTrustmarkDefinitionImpl(tdJson));
            }
        }
    }

    private URL nextPageUrl;
    private List<RemoteTrustmarkDefinition> objects = new ArrayList<>();

    @Override
    public Class getClassType() {
        return RemoteTrustmarkDefinition.class;
    }


    @Override
    public List<RemoteTrustmarkDefinition> getObjects() {
        if( objects == null )
            objects = new ArrayList<>();
        return objects;
    }

    public void addObject(RemoteTrustmarkDefinition td){
        this.getObjects().add(td);
    }

    @Override
    public Boolean hasNext() {
        return nextPageUrl != null;
    }

    @Override
    public Page<RemoteTrustmarkDefinition> next() throws RemoteException {
        if( nextPageUrl == null )
            throw new NoSuchElementException();
        return new TDPageImpl(IOUtils.fetchJSON(nextPageUrl));
    }//end next()


}//end TDPageImpl
