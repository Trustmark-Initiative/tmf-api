package edu.gatech.gtri.trustmark.v1_0.impl.util;

import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkDefinition;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkFrameworkIdentifiedObject;
import edu.gatech.gtri.trustmark.v1_0.util.TipTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 * @author brad
 * @date 5/9/17
 */
public class TipTreeNodeImpl implements TipTreeNode {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = LoggerFactory.getLogger(TipTreeNodeImpl.class);
    //==================================================================================================================
    //  CONSTRUCTORS
    //==================================================================================================================
    public TipTreeNodeImpl () {
    }

    public TipTreeNodeImpl(TrustmarkDefinition td){
        this.tmfi = td.getMetadata();
        this.trustmarkDefinition = td;
    }
    public TipTreeNodeImpl(TrustInteroperabilityProfile tip){
        this.tmfi = tip;
        this.trustInteropProfile = tip;
    }

    //==================================================================================================================
    //  INSTANCE VARIABLES
    //==================================================================================================================
    private TrustmarkFrameworkIdentifiedObject tmfi;
    private TrustmarkDefinition trustmarkDefinition;
    private TrustInteroperabilityProfile trustInteropProfile;
    private List<TipTreeNode> children = new ArrayList<>();
    //==================================================================================================================
    //  GETTERS / SETTERS
    //==================================================================================================================
    public TrustmarkFrameworkIdentifiedObject getTmfi() {
        return tmfi;
    }

    public void setTmfi(TrustmarkFrameworkIdentifiedObject tmfi) {
        this.tmfi = tmfi;
    }

    @Override
    public TrustmarkDefinition getTrustmarkDefinition() {
        return trustmarkDefinition;
    }

    public void setTrustmarkDefinition(TrustmarkDefinition trustmarkDefinition) {
        this.trustmarkDefinition = trustmarkDefinition;
    }

    @Override
    public TrustInteroperabilityProfile getTrustInteropProfile() {
        return trustInteropProfile;
    }

    public void setTrustInteropProfile(TrustInteroperabilityProfile trustInteropProfile) {
        this.tmfi = trustInteropProfile;
        this.trustInteropProfile = trustInteropProfile;
    }

    @Override
    public List<TipTreeNode> getChildren() {
        if( children == null )
            this.children = new ArrayList<>();
        return children;
    }

    public void setChildren(List<TipTreeNode> children) {
        this.children = children;
    }

    @Override
    public Throwable getError() {
        return null;
    }
    //==================================================================================================================
    //  ADDERS
    //==================================================================================================================
    public void addChild(TipTreeNode child){
        this.getChildren().add(child);
    }

    //==================================================================================================================
    //  PUBLIC METHODS
    //==================================================================================================================

    @Override
    public boolean isTrustmarkDefinition() {
        return getTmfi().getTypeName().equalsIgnoreCase("TrustmarkDefinition");
    }

    @Override
    public boolean isTrustInteropProfile() {
        if( getTmfi() != null ){
            if( getTmfi().getTypeName() != null ){
                return getTmfi().getTypeName().equalsIgnoreCase("TrustInteroperabilityProfile");
            }else{
                log.error("Tmfi TypeName is null!");
            }
        }else{
            log.error("Tmfi is null!");
        }
        return false;
    }

    @Override
    public boolean hasError() {
        return false;
    }

    @Override
    public String getTypeName() {
        return getTmfi().getTypeName();
    }

    @Override
    public URI getIdentifier() {
        return getTmfi().getIdentifier();
    }

    @Override
    public String getName() {
        return getTmfi().getName();
    }

    @Override
    public Integer getNumber() {
        return getTmfi().getNumber();
    }

    @Override
    public String getVersion() {
        return getTmfi().getVersion();
    }

    @Override
    public String getDescription() {
        return getTmfi().getDescription();
    }
}/* end TipTreeNodeImpl */
