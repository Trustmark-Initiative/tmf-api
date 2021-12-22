package edu.gatech.gtri.trustmark.v1_0.impl.model;

import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;

import java.net.URI;
import java.util.Objects;

/**
 * Created by brad on 12/7/15.
 */
public class TrustmarkFrameworkIdentifiedObjectImpl implements TrustmarkFrameworkIdentifiedObject {

    private String typeName;
    private URI identifier;
    private String name;
    private String description;
    private String version;
    private Integer number;

    @Override
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public URI getIdentifier() {
        return identifier;
    }

    public void setIdentifier(URI identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number){ this.number = number; }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    public boolean equals(TrustmarkFrameworkIdentifiedObjectImpl object){
        return Objects.equals(object.getIdentifier(), this.getIdentifier());
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof TrustmarkFrameworkIdentifiedObjectImpl)) {
            return false;
        }

        return this.equals((TrustmarkFrameworkIdentifiedObjectImpl) o);
    }
}
