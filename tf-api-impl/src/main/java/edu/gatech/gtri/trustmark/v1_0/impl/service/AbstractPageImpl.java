package edu.gatech.gtri.trustmark.v1_0.impl.service;

import edu.gatech.gtri.trustmark.v1_0.FactoryLoader;
import edu.gatech.gtri.trustmark.v1_0.io.NetworkDownloader;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

/**
 * Created by brad on 2/4/16.
 */
public abstract class AbstractPageImpl extends RemoteObjectImpl {

    protected long totalCount = -1l;
    protected long offset = -1l;
    protected long count = -1l;


    public long getTotalCount() {
        return totalCount;
    }


    public long getOffset() {
        return offset;
    }


    public long getCount() {
        return count;
    }

}
