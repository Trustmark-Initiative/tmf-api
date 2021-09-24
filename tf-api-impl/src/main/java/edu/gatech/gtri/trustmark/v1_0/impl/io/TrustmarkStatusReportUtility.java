package edu.gatech.gtri.trustmark.v1_0.impl.io;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.HttpResponse;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;
import edu.gatech.gtri.trustmark.v1_0.io.ParseException;
import edu.gatech.gtri.trustmark.v1_0.io.ResolveException;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportCache;
import edu.gatech.gtri.trustmark.v1_0.io.TrustmarkStatusReportResolver;
import edu.gatech.gtri.trustmark.v1_0.model.Trustmark;
import edu.gatech.gtri.trustmark.v1_0.model.TrustmarkStatusReport;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.net.URL;

public class TrustmarkStatusReportUtility {

    private static final Logger log = LogManager.getLogger(TrustmarkStatusReportUtility.class);

    private TrustmarkStatusReportUtility() {
    }

    public static TrustmarkStatusReport resolve(Trustmark trustmark, TrustmarkStatusReportResolver trustmarkStatusReportResolver) throws ResolveException {

        log.debug("Resolving status report for Trustmark (" + trustmark.getIdentifier() + ") ...");

        final TrustmarkStatusReportCache cache = FactoryLoader.getInstance(TrustmarkStatusReportCache.class);

        if (cache != null && cache.hasValidCacheEntry(trustmark)) {
            log.debug("Returning valid cache entry ...");
            return cache.getReportFromCache(trustmark);
        }

        final URL statusReportUrl = trustmark.getStatusURL();
        final NetworkDownloader networkDownloader = FactoryLoader.getInstance(NetworkDownloader.class);

        log.debug("Downloading latest status report from URL (" + statusReportUrl + ") ...");
        HttpResponse response = null;
        try {
            response = networkDownloader.download(statusReportUrl);
        } catch (IOException ioe) {
            throw new ResolveException("Cannot download the status report from URL (" + statusReportUrl + "): " + ioe.getMessage(), ioe);
        }

        final String statusReportData = response.getContent();
        if (StringUtils.isBlank(statusReportData))
            throw new ParseException("The status URL (" + statusReportUrl + ") did not return a status report");

        return trustmarkStatusReportResolver.resolve(statusReportData);
    }
}
