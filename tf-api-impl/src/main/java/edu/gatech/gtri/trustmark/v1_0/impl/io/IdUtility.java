package edu.gatech.gtri.trustmark.v1_0.impl.io;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class IdUtility {

    static final String Pattern = "yyyyMMddHHmmss";
    static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Pattern);

    private IdUtility() {

    }

    public static String trustmarkDefinitionId() {

        return "TD_" + dtf.format(LocalDateTime.now()) + "_" + UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

    public static String trustmarkId() {

        return "TM_" + dtf.format(LocalDateTime.now()) + "_" + UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

    public static String trustmarkStatusReportId() {

        return "TSR_" + dtf.format(LocalDateTime.now()) + "_" + UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

    public static String trustInteroperabilityProfileId() {

        return "TIP_" + dtf.format(LocalDateTime.now()) + "_" + UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

}
