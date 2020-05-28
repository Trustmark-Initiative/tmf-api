package edu.gatech.gtri.trustmark.v1_0.impl.io;

public class TrustInteroperabilityProfileSyntaxException extends Exception {

    private String tipName;

    public TrustInteroperabilityProfileSyntaxException(String msg, String tipName){
        super(msg);
        this.tipName = tipName;
    }

    public String setTipName() {
        return tipName;
    }
    public void setTipName(String tipName) {
        this.tipName = tipName;
    }
}
