package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.service.RemoteObject;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by brad on 2/4/16.
 */
public abstract class RemoteObjectImpl implements RemoteObject {

    private static final Logger log = Logger.getLogger(RemoteObjectImpl.class);

    protected Date getDateFromString( String string ){
        try{
            Long lval = Long.parseLong(string);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(lval);
            return c.getTime();
        }catch(Throwable t){
            return xmlStringToDate(string);
        }
    }
    protected Date xmlStringToDate(String xmlDateString ){
        return DatatypeConverter.parseDateTime(xmlDateString).getTime();
    }
    protected Map<String, URL> buildFormats(JSONObject linksJson ) throws MalformedURLException {
        Map<String, URL> formats = new HashMap<>();
        JSONArray formatsArray = linksJson.getJSONArray("_formats");
        for( int i = 0; i < formatsArray.length(); i++ ){
            JSONObject formatJSON = formatsArray.getJSONObject(i);
            if( log.isDebugEnabled() ) log.debug(String.format("Formatting as URL: %s", formatJSON.getString("href")));
            formats.put(formatJSON.getString("format"), new URL(formatJSON.getString("href")));
        }
        return formats;
    }

    private HashMap<String, URL> formats = new HashMap<>();

    @Override
    public HashMap<String, URL> getFormats() {
        if( formats == null )
            formats = new HashMap<>();
        return formats;
    }

    public void setFormats(Map<String, URL> formats) {
        this.formats = new HashMap<>();
        if( formats != null )
            this.formats.putAll(formats);
    }

    public void addFormat( String format, URL url ){
        this.getFormats().put(format, url);
    }

}
