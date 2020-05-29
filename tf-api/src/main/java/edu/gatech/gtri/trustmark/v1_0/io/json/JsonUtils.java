package edu.gatech.gtri.trustmark.v1_0.io.json;

import edu.gatech.gtri.trustmark.v1_0.io.xml.XmlUtils;

import java.util.Date;

/**
 * Provides globally recognized JSON helper methods, in this system.
 * <br/><br/>
 * Created by brad on 1/7/16.
 */
public final class JsonUtils {


    /**
     * Converts the given java date into a JSON friendly date format.
     */
    public static String toDateTimeString( Date date ){
        return XmlUtils.toDateTimeString(date);
    }//end toDateTimeString()



}//end JsonUtils