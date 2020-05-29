package edu.gatech.gtri.trustmark.v1_0.impl.util;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.util.TipTreeNode;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brad on 5/13/17.
 */
public class TipTreeNodeErrorImpl implements TipTreeNode {

    public TipTreeNodeErrorImpl(Throwable t, URI identifier){
        this.error = t;
        this.identifier = identifier;
    }

    private Throwable error;
    private URI identifier;


    @Override
    public String getTypeName() {
        if( this.isTrustInteropProfile() )
            return TrustInteroperabilityProfile.class.getSimpleName();
        else if( this.isTrustmarkDefinition() )
            return TrustmarkDefinition.class.getSimpleName();
        else
            return null;
    }

    @Override
    public boolean hasError() {
        return true;
    }

    @Override
    public URI getIdentifier() {
        return identifier;
    }

    @Override
    public Throwable getError() {
        return error;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Integer getNumber() {
        return null;
    }

    @Override
    public boolean isTrustmarkDefinition() {
        return this.identifier.toString().contains("/trustmark-definitions");
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public TrustmarkDefinition getTrustmarkDefinition() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean isTrustInteropProfile() {
        return this.identifier.toString().contains("/trust-interoperability-profiles");
    }

    @Override
    public TrustInteroperabilityProfile getTrustInteropProfile() {
        return null;
    }

    @Override
    public List<TipTreeNode> getChildren() {
        return new ArrayList<>();
    }
}
