package edu.gatech.gtri.trustmark.v1_0.model;

/**
 * Created by brad on 5/20/16.
 */
public enum ParameterKind {
    /**
     * A string value.  This is the "default" type, and any types not listed in this enumeration should use this field.
     * For example, XML and JSON values can go into this field.
     */
    STRING,
    /**
     * Should be a decimal value, including but not limited to integers, doubles, etc.
     */
    NUMBER,
    /**
     * A true/false value.
     */
    BOOLEAN,
    /**
     * A date timestamp - the entire timestamp to the millisecond.
     */
    DATETIME,
    /**
     * Indicates that the value is one of the values given in the enumeration section of the Parameter definition.
     */
    ENUM,
    /**
     * Indicates that the value is zero or more of the values given in the enumeration section of the Parameter definition.
     */
    ENUM_MULTI;

    /**
     * Compares the given string t
     */
    public static ParameterKind fromString( String str ){
        String strCmp = str;
        if( strCmp == null ) strCmp = "";
        strCmp = strCmp.trim();
        ParameterKind kind = null;
        for( ParameterKind cur : ParameterKind.values() ){
            if( cur.toString().equalsIgnoreCase(strCmp) ){
                kind = cur;
                break;
            }
        }
        return kind;
    }
    
    /**
     * Includes constant values useful for reading or writing parameter values.
     */
    public static interface IOConstants {
        /**
         * The separator character to be used when reading or writing multiple selected ENUM_MULTI values.
         */
        public static final String ENUM_MULTI_SEPARATOR = "|";
    }
}
