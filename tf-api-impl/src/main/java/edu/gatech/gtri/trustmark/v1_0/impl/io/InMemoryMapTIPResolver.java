package edu.gatech.gtri.trustmark.v1_0.impl.io;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustInteroperabilityProfileResolver;
import edu.gatech.gtri.trustmark.v1_0.model.TrustInteroperabilityProfile;

public class InMemoryMapTIPResolver implements TrustInteroperabilityProfileResolver {

	protected final Map<URI, TrustInteroperabilityProfile> tipMap;
	
	public InMemoryMapTIPResolver(Map<URI, TrustInteroperabilityProfile> tipMap) {
		this.tipMap = tipMap;
	}
	
	public InMemoryMapTIPResolver() {
		this.tipMap = new HashMap<URI, TrustInteroperabilityProfile>();
	}
	
	public Map<URI, TrustInteroperabilityProfile> getTipMap() {
		return tipMap;
	}
	
	public void addTIP(TrustInteroperabilityProfile tip) {
		tipMap.put(tip.getIdentifier(), tip);
	}
	
	@Override
	public TrustInteroperabilityProfile resolve(URI tipId) throws ParseException {
		TrustInteroperabilityProfile tip = tipMap.get(tipId);
		if(tip == null)
			throw new ParseException("Not found.");
		return tip; 
	}

	@Override
	public TrustInteroperabilityProfile resolve(URL tipURL) throws ResolveException {
		return resolve(tipURL, false);
	}

	@Override
	public TrustInteroperabilityProfile resolve(URL tipURL, Boolean validate) throws ResolveException {
		throw new UnsupportedOperationException("NOT IMPLEMENTED");
	}

	@Override
	public TrustInteroperabilityProfile resolve(URI tipURI, Boolean validate) throws ParseException {
		return resolve(tipURI);
	}

	@Override
	public TrustInteroperabilityProfile resolve(File tipFile) throws ParseException {
		throw new UnsupportedOperationException("NOT IMPLEMENTED");
	}

	@Override
	public TrustInteroperabilityProfile resolve(File tipFile, Boolean validate) throws ParseException {
		throw new UnsupportedOperationException("NOT IMPLEMENTED");
	}

	@Override
	public TrustInteroperabilityProfile resolve(InputStream tipInputStream) throws ParseException {
		throw new UnsupportedOperationException("NOT IMPLEMENTED");
	}

	@Override
	public TrustInteroperabilityProfile resolve(InputStream tipInputStream, Boolean validate) throws ParseException {
		throw new UnsupportedOperationException("NOT IMPLEMENTED");
	}

	@Override
	public TrustInteroperabilityProfile resolve(Reader tipReader) throws ParseException {
		throw new UnsupportedOperationException("NOT IMPLEMENTED");
	}

	@Override
	public TrustInteroperabilityProfile resolve(Reader tipReader, Boolean validate) throws ParseException {
		throw new UnsupportedOperationException("NOT IMPLEMENTED");
	}

	@Override
	public TrustInteroperabilityProfile resolve(String rawTipString) throws ParseException {
		throw new UnsupportedOperationException("NOT IMPLEMENTED");
	}

	@Override
	public TrustInteroperabilityProfile resolve(String rawTipString, Boolean validate) throws ParseException {
		throw new UnsupportedOperationException("NOT IMPLEMENTED");
	}
}
