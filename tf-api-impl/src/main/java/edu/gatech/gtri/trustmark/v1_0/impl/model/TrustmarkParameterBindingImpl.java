package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.ParameterKind;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkParameterBinding;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.String.format;

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
        return parameterKind.match(
                parameterKindBoolean -> getBooleanValue(),
                parameterKindDatetime -> getDateTimeValue(),
                parameterKindEnum -> getStringValue(),
                parameterKindEnumMulti -> getStringListValue(),
                parameterKindNumber -> getNumericValue(),
                parameterKindString -> getStringValue(),
                parameterKindOther -> {
                    throw new RuntimeException("ParameterKind must be one of BOOLEAN, DATETIME, ENUM, ENUM_MULTI, NUMBER, and STRING.");
                }
        );
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
    public BigDecimal getNumericValue() {
        return parameterKind.match(
                parameterKindBoolean -> null,
                parameterKindDatetime -> null,
                parameterKindEnum -> null,
                parameterKindEnumMulti -> null,
                parameterKindNumber -> {
                    try {
                        return new BigDecimal(getStringValue());
                    } catch (Throwable t) {
                        throw new CoerceTypeError(format("Cannot coerce '%s' into a Number!", getStringValue()), t);
                    }
                },
                parameterKindString -> null,
                parameterKindOther -> {
                    throw new RuntimeException("ParameterKind must be one of BOOLEAN, DATETIME, ENUM, ENUM_MULTI, NUMBER, and STRING.");
                }
        );
    }

    @Override
    public Boolean getBooleanValue() {
        return parameterKind.match(
                parameterKindBoolean -> {
                    try {
                        return Boolean.parseBoolean(getStringValue());
                    } catch (Throwable t) {
                        throw new CoerceTypeError(format("Cannot coerce '%s' into a Boolean!", getStringValue()), t);
                    }
                },
                parameterKindDatetime -> null,
                parameterKindEnum -> null,
                parameterKindEnumMulti -> null,
                parameterKindNumber -> null,
                parameterKindString -> null,
                parameterKindOther -> {
                    throw new RuntimeException("ParameterKind must be one of BOOLEAN, DATETIME, ENUM, ENUM_MULTI, NUMBER, and STRING.");
                }
        );
    }

    @Override
    public Instant getDateTimeValue() {
        return parameterKind.match(
                parameterKindBoolean -> null,
                parameterKindDatetime -> {
                    try {
                        return Instant.ofEpochMilli(Long.parseLong(getStringValue()));
                    } catch (Throwable t) {
                        try {
                            return Instant.parse(getStringValue());
                        } catch (final DateTimeParseException dateTimeParseException) {
                            try {
                                return LocalDateTime.parse(getStringValue()).toInstant(ZoneOffset.UTC);
                            } catch (Throwable t2) {
                                throw new CoerceTypeError(format("Cannot coerce '%s' into a timestamp!  It should be a time in millis or a XML Date Timestamp format.", getStringValue()));
                            }
                        }
                    }
                },
                parameterKindEnum -> null,
                parameterKindEnumMulti -> null,
                parameterKindNumber -> null,
                parameterKindString -> null,
                parameterKindOther -> {
                    throw new RuntimeException("ParameterKind must be one of BOOLEAN, DATETIME, ENUM, ENUM_MULTI, NUMBER, and STRING.");
                }
        );
    }

    @Override
    public List<String> getStringListValue() {
        return parameterKind.match(
                parameterKindBoolean -> null,
                parameterKindDatetime -> null,
                parameterKindEnum -> null,
                parameterKindEnumMulti -> {
                    try {
                        return Arrays.asList(getStringValue().split(Pattern.quote(ParameterKind.IOConstants.ENUM_MULTI_SEPARATOR)));
                    } catch (Throwable t) {
                        throw new CoerceTypeError(format("Cannot coerce '%s' into a List<String>!", getStringValue()), t);
                    }
                },
                parameterKindNumber -> null,
                parameterKindString -> null,
                parameterKindOther -> {
                    throw new RuntimeException("ParameterKind must be one of BOOLEAN, DATETIME, ENUM, ENUM_MULTI, NUMBER, and STRING.");
                }
        );
    }

    @Override
    public int compareTo(TrustmarkParameterBinding o) {
        return getIdentifier().compareToIgnoreCase(o.getIdentifier());
    }
}
