package edu.gatech.gtri.trustmark.v1_0.io;

import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

/**
 * Retrieves trustmark status reports (TSRs) for trustmarks.
 * 
 * @author GTRI Trustmark Team
 *
 */
public interface TrustmarkStatusReportResolver {

	/**
	 * Resolves the TrustmarkStatusReport for the given Trustmark.
     */
	public TrustmarkStatusReport resolve(Trustmark trustmark) throws ResolveException;



	public TrustmarkStatusReport resolve(URL url) throws ResolveException;
	public TrustmarkStatusReport resolve(URI uri) throws ResolveException;
	public TrustmarkStatusReport resolve(File file) throws ResolveException;
	public TrustmarkStatusReport resolve(InputStream trustmarkInputStream) throws ResolveException;
	public TrustmarkStatusReport resolve(Reader trustmarkReader) throws ResolveException;
	public TrustmarkStatusReport resolve(String utf8EncodedTrustmarkString) throws ResolveException;

}
