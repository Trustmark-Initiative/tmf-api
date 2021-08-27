package edu.gatech.gtri.trustmark.v1_0.impl.io;

import java.util.UUID;

public final class IdUtility {
    private IdUtility() {

    }

    public static String trustmarkDefinitionId() {

        return "TD_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

    public static String trustmarkId() {

        return "TM_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

    public static String trustmarkStatusReportId() {

        return "TSR_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

    public static String trustInteroperabilityProfileId() {

        return "TIP_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

}
