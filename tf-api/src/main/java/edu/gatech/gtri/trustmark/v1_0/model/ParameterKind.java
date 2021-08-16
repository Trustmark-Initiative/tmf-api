package edu.gatech.gtri.trustmark.v1_0.model;

import org.gtri.fj.function.F1;

import static java.util.Objects.requireNonNull;

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
    public static ParameterKind fromString(String str) {
        String strCmp = str;
        if (strCmp == null) strCmp = "";
        strCmp = strCmp.trim();
        ParameterKind kind = null;
        for (ParameterKind cur : ParameterKind.values()) {
            if (cur.toString().equalsIgnoreCase(strCmp)) {
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

    public <T1> T1 match(
            F1<ParameterKind, T1> fParameterKindBoolean,
            F1<ParameterKind, T1> fParameterKindDatetime,
            F1<ParameterKind, T1> fParameterKindEnum,
            F1<ParameterKind, T1> fParameterKindEnumMulti,
            F1<ParameterKind, T1> fParameterKindNumber,
            F1<ParameterKind, T1> fParameterKindString,
            F1<ParameterKind, T1> f) {

        requireNonNull(fParameterKindBoolean);
        requireNonNull(fParameterKindDatetime);
        requireNonNull(fParameterKindEnum);
        requireNonNull(fParameterKindEnumMulti);
        requireNonNull(fParameterKindNumber);
        requireNonNull(fParameterKindString);
        requireNonNull(f);

        return
                this == BOOLEAN ?
                        fParameterKindBoolean.f(this) :
                        this == DATETIME ?
                                fParameterKindDatetime.f(this) :
                                this == ENUM ?
                                        fParameterKindEnum.f(this) :
                                        this == ENUM_MULTI ?
                                                fParameterKindEnumMulti.f(this) :
                                                this == NUMBER ?
                                                        fParameterKindNumber.f(this) :
                                                        this == STRING ?
                                                                fParameterKindString.f(this) :
                                                                f.f(this);
    }

}
