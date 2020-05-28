package edu.gatech.gtri.trustmark.v1_0.util.diff;

/**
 * When performing a diff, some changes are minor (ie, small text changes here or there) and some are major (ie, a complete
 * restructuring).  For example, if a TD's description is different from another, then we would say that's minor.  However,
 * if the TD's assessment step count is different, then we would say that's major.  Each object type that can be diff'd
 * has its own internal definition of major and minor differences.
 * <br/><br/>
 * Created by brad on 4/12/16.
 */
public enum DiffSeverity {

    MINOR,
    MAJOR;

}
