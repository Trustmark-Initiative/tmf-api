package edu.gatech.gtri.trustmark.v1_0.model;

public enum ContactKindCode {

	/**
	 * The contact is the primary contact for the organization or business
	 * entity.
	 */
	PRIMARY,

	/**
	 * The contact is not the primary contact for the organization or business
	 * entity.
	 */
	OTHER;


	public static ContactKindCode fromString( String str ){
		ContactKindCode kindCode = null;
		for( ContactKindCode current : ContactKindCode.values() ){
			if( current.toString().equalsIgnoreCase(str.trim()) ){
				kindCode = current;
				break;
			}
		}
		return kindCode;
	}

}
