package edu.gatech.gtri.trustmark.v1_0.service;

import edu.gatech.gtri.trustmark.v1_0.model.Entity;

/**
 * Created by brad on 3/23/17.
 */
public interface RemoteOrganization extends Entity {

    /**
     * A short concise representation of this organization, such as GTRI, FBI, or CIA.  "TMI Core" and "TMI Lib" are
     * also used in the architecture.  Nothing over 15 or so characters.
     */
    public String getAbbreviation();

    /**
     * Contains a field which *should* point to a logo.  If the value is empty or null, then the default logo should
     * be shown for this organization.
     */
    public String getLogoImagePath();

}
