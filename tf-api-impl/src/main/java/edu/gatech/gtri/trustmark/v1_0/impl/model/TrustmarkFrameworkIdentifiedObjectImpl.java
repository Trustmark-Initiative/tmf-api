package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;

import java.net.URI;
import java.util.Objects;

public class TrustmarkFrameworkIdentifiedObjectImpl implements TrustmarkFrameworkIdentifiedObject {

    private String typeName;
    private URI identifier;
    private String name;
    private Integer number;
    private String version;
    private String description;

    public TrustmarkFrameworkIdentifiedObjectImpl() {
        this(
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public TrustmarkFrameworkIdentifiedObjectImpl(
            final String typeName,
            final URI identifier,
            final String name,
            final Integer number,
            final String version,
            final String description) {
        this.typeName = typeName;
        this.identifier = identifier;
        this.name = name;
        this.number = number;
        this.version = version;
        this.description = description;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    @Override
    public URI getIdentifier() {
        return identifier;
    }

    public void setIdentifier(final URI identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TrustmarkFrameworkIdentifiedObjectImpl that = (TrustmarkFrameworkIdentifiedObjectImpl) o;
        return Objects.equals(typeName, that.typeName) && Objects.equals(identifier, that.identifier) && Objects.equals(name, that.name) && Objects.equals(number, that.number) && Objects.equals(version, that.version) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName, identifier, name, number, version, description);
    }
}
