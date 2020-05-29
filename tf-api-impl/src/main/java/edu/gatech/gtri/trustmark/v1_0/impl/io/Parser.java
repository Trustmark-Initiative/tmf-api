package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.io.ParseException;

/**
 * A function that parses from a string to the give type.
 */
@FunctionalInterface
public interface Parser<T> {
    
    // Constants
    Parser<String> IDENTITY = x -> x;
    
    // Abstract Methods
    T doParse(String value) throws Exception;
    
    // Default methods
    default T parseOrNull(String value) throws ParseException {
        try { return value == null ? null : this.doParse(value); }
        catch (ParseException ex) { throw ex; }
        catch (Exception ex) { throw new ParseException(ex); }
    }
    
}
