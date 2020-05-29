package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkParameterBinding;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by brad on 5/20/16.
 */
public class TrustmarkParameterBindingImpl implements TrustmarkParameterBinding, Comparable<TrustmarkParameterBinding> {

    private String identifier;
    private String value;
    private ParameterKind parameterKind;

    @Override
    public String getIdentifier() {
        return identifier;
    }
    @Override
    public Object getValue() {
        return value;
    }
    public ParameterKind getParameterKind() {
        return parameterKind;
    }
    @Override
    public String getStringValue() {
        return value;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setParameterKind(ParameterKind parameterKind) {
        this.parameterKind = parameterKind;
    }

    @Override
    public Number getNumericValue() {
        if( this.getParameterKind() == ParameterKind.NUMBER ){
            try{
                return Double.parseDouble(this.getStringValue());
            }catch(Throwable t){
                throw new CoerceTypeError("Cannot coerce '"+this.getStringValue()+"' into a Number!", t);
            }
        }
        return null;
    }

    @Override
    public Boolean getBooleanValue() {
        if( this.getParameterKind() == ParameterKind.BOOLEAN ){
            try{
                return Boolean.parseBoolean(this.getStringValue());
            }catch(Throwable t){
                throw new CoerceTypeError("Cannot coerce '"+this.getStringValue()+"' into a Boolean!", t);
            }
        }
        return null;
    }

    @Override
    public Calendar getDateTimeValue() {
        if( this.getParameterKind() == ParameterKind.DATETIME ){
            long millisTime = -1;
            try{
                millisTime = Long.parseLong(this.getStringValue());
            }catch(Throwable t){
                try {
                    return DatatypeConverter.parseDateTime(this.getStringValue());
                }catch(Throwable t2){
                    throw new CoerceTypeError("Cannot coerce '"+this.getStringValue()+"' into a timestamp!  It should be a time in millis or a XML Date Timestamp format.");
                }
            }
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(millisTime);
            return c;
        }
        return null;
    }
    
    @Override
    public List<String> getStringListValue() {
        if (this.getParameterKind() == ParameterKind.ENUM_MULTI ){
            try{
                String[] valueArray = this.getStringValue().split(Pattern.quote(ParameterKind.IOConstants.ENUM_MULTI_SEPARATOR));
                return Arrays.asList(valueArray);
            }catch (Throwable t) {
                throw new CoerceTypeError("Cannot coerce '"+this.getStringValue()+"' into a String List!", t);
            }
        }
        return null;
    }
    
    @Override
    public int compareTo(TrustmarkParameterBinding o) {
        return this.getIdentifier().compareToIgnoreCase(o.getIdentifier());
    }
}
