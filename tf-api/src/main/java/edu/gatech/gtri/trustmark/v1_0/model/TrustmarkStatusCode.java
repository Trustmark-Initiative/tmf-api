package edu.gatech.gtri.trustmark.v1_0.model;

/**
 * Constrains the value space of the StatusCode property of a Trustmark Status
 * Report to one of the following string values: "ACTIVE", "REVOKED", and
 * "EXPIRED".
 * 
 * @author GTRI Trustmark Team
 *
 */
public enum TrustmarkStatusCode {

	ACTIVE, REVOKED, EXPIRED;


	public static TrustmarkStatusCode fromString( String str ){
		TrustmarkStatusCode code = null;
		for( TrustmarkStatusCode current : TrustmarkStatusCode.values() ){
			if( current.toString().equalsIgnoreCase(str.trim()) ){
				code = current;
				break;
			}
		}
		return code;
	}

}
