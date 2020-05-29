package edu.gatech.gtri.trustmark.v1_0.io.xml;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Provides globally recognized JSON helper methods, in this system.
 * <br/><br/>
 * Created by brad on 1/7/16.
 */
public final class XmlUtils {


    /**
     * Converts the given java date into an XML friendly date format.
     */
    public static String toDateTimeString( Date date ){
        if( date == null )
            return null;

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        DatatypeFactory df = null;
        try{
            df = DatatypeFactory.newInstance();
        }catch(Throwable t){
            throw new UnsupportedOperationException("Unable to configure XML DatatypeFactory!", t);
        }
        XMLGregorianCalendar dateTime = df.newXMLGregorianCalendar(calendar);
        return dateTime.toString();
    }//end toDateTimeString()



}//end JsonUtils